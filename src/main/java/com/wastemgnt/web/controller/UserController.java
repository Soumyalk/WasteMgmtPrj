package com.wastemgnt.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wastemgnt.service.entity.EntityType;
import com.wastemgnt.service.entity.Order;
import com.wastemgnt.service.entity.User;
import com.wastemgnt.service.entity.UserRole;
import com.wastemgnt.service.services.OrderService;
import com.wastemgnt.service.services.StateCityConfigService;
import com.wastemgnt.service.services.UserRoleService;
import com.wastemgnt.service.services.UserService;
import com.wastemgnt.web.model.UserForm;
import com.wastemgnt.web.utils.Utilities;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService roleService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private StateCityConfigService stateConfig;

	@Value("${mail.passsword}")
	private String password;

	@Value("${mail.from}")
	private String fromEmail;

	@RequestMapping(value = "/admin/users", method = RequestMethod.GET)
	public String showUsers(Model model) {
		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		List<User> users = userService.findAll();
		model.addAttribute("usersList", users);
		return "users";
	}

	@RequestMapping(value = "/admin/users/create", method = RequestMethod.GET)
	public String addUser(Model model) {
		String name = Utilities.getLoggedInUserName(model);
		if (null == model.asMap().get("userForm")) {
			model.addAttribute("userForm", new UserForm());
		}
		model.addAttribute("genderList", Utilities.getGenderTypes());
		model.addAttribute("login", name);
		return "adduser";
	}

	@RequestMapping(value = "/admin/user/changepwd", method = RequestMethod.GET)
	public String changePassWord(Model model) {
		String loginId = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", loginId);
		User user = userService.findByLoginId(loginId);
		if (null == model.asMap().get("userForm")) {
			UserForm entity = new UserForm(user);
			model.addAttribute("userForm", entity);
		}
		return "changePassword";
	}

	@RequestMapping(value = "/admin/user/savepwd", method = RequestMethod.POST)
	public String savePassWord(Model model, @ModelAttribute("userForm") UserForm userForm) {
		String loginId = Utilities.getLoggedInUserName(model);
		User user = userService.findByLoginId(loginId);
		String actualPwd = user.getPassword();
		String changedPwd = userForm.getChangedPwd();
		String confirmPwd = userForm.getConfirmPwd();
		if (changedPwd.equals(confirmPwd)) {
			if (actualPwd.equals(userForm.getPassword())) {
				user.setPassword(changedPwd);
				userService.save(user);
				model.addAttribute("msg", "Password changed successfully");
			} else {
				model.addAttribute("error", "Old password is not matched");
			}
		} else {
			model.addAttribute("error", "New password is not matched");
		}
		return "redirect:/logout";
	}

	@RequestMapping(value = "/admin/users/save", method = RequestMethod.POST)
	public String createUser(Model model, @ModelAttribute("userForm") UserForm userForm) {
		User user = userService.findByLoginId(userForm.getLoginId());
		if (null == user) {
			user = new User();
			user.setCreatedBy("test");
			user.setCreatedOn(new Date());
			user.setFirstName(userForm.getFirstName());
			user.setLastName(userForm.getLastName());
			user.setLoginId(userForm.getLoginId());
			user.setGender(userForm.getGender());
			user.setPassword(userForm.getPassword());
			user.setPhone(userForm.getPhone());
			user.setEmail(userForm.getEmail());
			user.setUserEnabled(false);
			user.setUserRoles(new ArrayList<>());
			user = userService.save(user);
			return "redirect:/admin/users/edit?loginId=" + user.getLoginId();
		} else {
			model.addAttribute("error", "User ID is already taken. please use different login ID");
			model.addAttribute("userForm", userForm);
			return addUser(model);
		}
	}

	@RequestMapping(value = "/admin/users/edit", method = RequestMethod.GET)
	public String editUser(Model model, @RequestParam("loginId") String loginId) {

		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		User user = userService.findByLoginId(loginId);
		UserForm form = new UserForm(user);
		List<UserRole> list = StreamSupport.stream(roleService.findAll().spliterator(), false)
				.filter(role -> !role.getRoleName().equals("SYSADMIN")).collect(Collectors.toList());
		model.addAttribute("entityTypes", Utilities.getEntityTypes());
		model.addAttribute("genderList", Utilities.getGenderTypes());
		model.addAttribute("stateList", stateConfig.findAll());
		if (!Utilities.isSystemAdmin(user)) {
			model.addAttribute("roles", list);
		}
		model.addAttribute("userForm", form);
		return "userEdit";
	}

	@RequestMapping(value = "/admin/users/delete", method = RequestMethod.GET)
	public String deleteUser(Model model, @RequestParam("loginId") String loginId) {

		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		User u = userService.findByLoginId(loginId);
		if (!u.getLoginId().equals(name)) {
			if (!u.getUserRoles().stream().anyMatch(role -> role.getRoleName().equals("SYSADMIN"))) {
				for (Order o : orderService.getAssigneeOrders(u.getId())) {
					o.setAssignee(null);
					orderService.save(o);
				}
				userService.delete(u);
			}
		} else {
			model.addAttribute("error", "Logged user cannot be deleted");
			return showUsers(model);
		}
		return "redirect:/admin/users";
	}

	@RequestMapping(value = "/admin/users/update", method = RequestMethod.POST)
	public String saveUser(Model model, @ModelAttribute("userForm") UserForm userForm) {
		User user = userService.findByLoginId(userForm.getLoginId());
		boolean isUserActivated = !user.isUserEnabled() && userForm.isUserEnabled();
		boolean isuserDeActivated = user.isUserEnabled() && !userForm.isUserEnabled();
		user.setUserEnabled(userForm.isUserEnabled());
		if ("INDIVIDUAL".equals(userForm.getSelectedRole())) {
			user.setEntityType(null);
		} else if (null != userForm.getEntityName() && !userForm.getEntityName().isEmpty()) {
			EntityType entityType = new EntityType();
			entityType.setEntityName(userForm.getEntityName());
			entityType.setAddress(userForm.getAddress());
			entityType.setEntityDescription(userForm.getDescription());
			entityType.setStateConfig(userForm.getStateConfig());
			user.setEntityType(entityType);
		}
		List<UserRole> list = new ArrayList<>();
		list.add(userForm.getRole());
		user.setUserRoles(list);
		user.setGender(userForm.getGender());
		userService.save(user);
		if (isUserActivated || isuserDeActivated) {
			Utilities.sendMail(fromEmail, user.getEmail(),
					"Your id is" + (isUserActivated ? " activated " : "Deativated") + "successfully. Please login!", "User Active",
					password);
		}
		return "redirect:/admin/users";
	}

	@RequestMapping(value = "/admin/profile", method = RequestMethod.GET)
	public String editProfile(Model model) {

		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		User loggedUser = userService.findByLoginId(name);
		UserForm form = new UserForm(loggedUser);

		List<String> genderList = new ArrayList<>();
		genderList.add("Male");
		genderList.add("Female");
		model.addAttribute("genderList", genderList);
		model.addAttribute("enableEntity", true);
		if (Utilities.isUserHasRole(loggedUser, "CUSTOMER")) {
			model.addAttribute("entityTypes", Utilities.getEntityTypes());
			model.addAttribute("stateList", stateConfig.findAll());
		} else {
			model.addAttribute("enableEntity", false);
		}
		model.addAttribute("entity", form);
		return "editProfile";
	}

	@RequestMapping(value = "/admin/profile/update", method = RequestMethod.POST)
	public String saveProfile(Model model, @ModelAttribute("userForm") UserForm userForm) {
		String name = Utilities.getLoggedInUserName(model);
		User user = userService.findByLoginId(name);
		user.setFirstName(userForm.getFirstName());
		user.setLastName(userForm.getLastName());
		user.setEmail(userForm.getEmail());
		user.setPhone(userForm.getPhone());
		user.setGender(userForm.getGender());
		if ("INDIVIDUAL".equals(userForm.getSelectedRole())) {
			user.setEntityType(null);
		} else if (null != userForm.getEntityName() && !userForm.getEntityName().isEmpty()) {
			EntityType entityType = null != user.getEntityType() ? user.getEntityType() : new EntityType();
			entityType.setEntityName(userForm.getEntityName());
			entityType.setAddress(userForm.getAddress());
			entityType.setEntityDescription(userForm.getDescription());
			entityType.setStateConfig(userForm.getStateConfig());
			user.setEntityType(entityType);
		}
		userService.save(user);
		return "redirect:/dashboard";
	}

}
