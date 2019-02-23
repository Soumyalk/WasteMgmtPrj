package com.wastemgnt.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wastemgnt.service.entity.EntityType;
import com.wastemgnt.service.entity.User;
import com.wastemgnt.service.entity.UserRole;
import com.wastemgnt.service.services.StateCityConfigService;
import com.wastemgnt.service.services.UserRoleService;
import com.wastemgnt.service.services.UserService;
import com.wastemgnt.web.model.UserForm;
import com.wastemgnt.web.utils.Utilities;

@Controller
public class LoginController {
	
	@Value("${mail.passsword}")
	private String password;
	
	@Value("${mail.from}")
	private String fromEmail;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService roleService;

	@Autowired
	private StateCityConfigService stateConfig;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginPage(ModelMap model) {
		return "login";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String showRegistrationPage(Model model) {
		if (null == model.asMap().get("userForm")) {
			model.addAttribute("userForm", new UserForm());
		}
		model.addAttribute("roles", Utilities.getEntityTypes());

		model.addAttribute("stateList", stateConfig.findAll());
		return "registration";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@Transactional
	public String registration(Model model, @ModelAttribute("userForm") UserForm userForm) {

		try {
			User user = userService.findByLoginId(userForm.getLoginId());
			if (null == user) {
				user = new User();
				user.setCreatedBy(userForm.getLoginId());
				user.setCreatedOn(new Date());
				user.setFirstName(userForm.getFirstName());
				user.setLastName(userForm.getLastName());
				user.setLoginId(userForm.getLoginId());
				user.setPassword(userForm.getPassword());
				user.setGender(userForm.getGender());
				user.setPhone(userForm.getPhone());
				user.setEmail(userForm.getEmail());
				user.setUserEnabled(false);
				if (null != userForm.getEntityName() && !userForm.getEntityName().isEmpty()) {
					EntityType entityType = new EntityType();
					entityType.setEntityName(userForm.getEntityName());
					entityType.setAddress(userForm.getAddress());
					entityType.setEntityDescription(userForm.getDescription());
					entityType.setStateConfig(userForm.getStateConfig());
					user.setEntityType(entityType);
				}
				List<UserRole> role = new ArrayList<>();
				role.add(roleService.findByName("CUSTOMER"));
				user.setUserRoles(role);
				user = userService.save(user);
				model.addAttribute("msg",
						"Registration has been done successfully.Please wait until Admin approval to login into system");
				//for sending mails.
				Utilities.sendMail(fromEmail, user.getEmail(), " Registration has been sent succcessfully, please login  once your id is activated.", "Registartion", password);
				return "registration";
			} else {
				model.addAttribute("error", "User ID is already taken. please use different login ID");
			}
		} catch (Exception ex) {
			model.addAttribute("error", "Something wentWrong please contact system administrator");
		}
		model.addAttribute("userForm", userForm);
		return showRegistrationPage(model);

	}

}
