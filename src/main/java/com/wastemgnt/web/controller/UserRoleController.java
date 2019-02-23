package com.wastemgnt.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wastemgnt.service.entity.Permission;
import com.wastemgnt.service.entity.User;
import com.wastemgnt.service.entity.UserRole;
import com.wastemgnt.service.services.PermissionService;
import com.wastemgnt.service.services.UserRoleService;
import com.wastemgnt.service.services.UserService;
import com.wastemgnt.web.model.UserRoleForm;

@Controller("")
public class UserRoleController {

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private UserService uerService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		Object target = binder.getTarget();
		if (target == null) {
			return;
		}

	}
	
	@RequestMapping(value = "/admin/userroles", method = RequestMethod.GET)
	public String showUserRoles(Model model) {

		String name = getLoggedInUserName(model);
		model.addAttribute("login", name);
		List<UserRole> userRoles = userRoleService.findAll();
		model.addAttribute("userRolesList", userRoles);
		return "userroles";
	}
	
	@RequestMapping(value = "/admin/userroles/create", method = RequestMethod.GET)
	public String createUserRole(Model model) {

		String name = getLoggedInUserName(model);
		model.addAttribute("login", name);
		UserRoleForm entity = new UserRoleForm();
		model.addAttribute("entity", entity);
		return "createUserRole";
	}
	
	@RequestMapping(value = "/admin/userroles/save", method = RequestMethod.POST)
	public String saveUserRole(Model model, @ModelAttribute("entity") UserRoleForm entity) {
		UserRole p = new UserRole();
		p.setRoleName(entity.getRoleName());
		p.setDescription(entity.getDesc());
		userRoleService.save(p);
		return "redirect:/admin/userroles";
	}
	

	@RequestMapping(value = "/admin/userroles/edit", method = RequestMethod.GET)
	public String editUserRole(Model model, @RequestParam("pId") Long pId) {

		String name = getLoggedInUserName(model);
		model.addAttribute("login", name);
		UserRoleForm permission = new UserRoleForm(userRoleService.findById(pId).get());
		model.addAttribute("entity", permission);
		return "userrole";
	}
	
	@RequestMapping(value = "/admin/userroles/delete", method = RequestMethod.GET)
	public String deleteUserRole(Model model, @RequestParam("pId") Long pId) {
		UserRole role = userRoleService.findById(pId).get();
		for(User user: uerService.findAll()) {
			if(user.getUserRoles().contains(role)) {
				user.getUserRoles().remove(role);
				uerService.save(user);
			}
		}
		userRoleService.delete(role);
		return "redirect:/admin/userroles";
	}
	
	@RequestMapping(value = "/admin/userroles/update", method = RequestMethod.POST)
	public String updateUserRole(Model model, @ModelAttribute("entity") UserRoleForm entity) {
		UserRole p = new UserRole();
		p.setId(entity.getId());
		p.setDescription(entity.getDesc());
		p.setRoleName(entity.getRoleName());
		userRoleService.save(p);
		return "redirect:/admin/userroles";
	}
	

	@RequestMapping(value = "/admin/userroles/detail", method = RequestMethod.GET)
	public String detailUserRole(Model model, @RequestParam("pId") Long pId) {

		String name = getLoggedInUserName(model);
		model.addAttribute("login", name);
		UserRoleForm permission = new UserRoleForm(userRoleService.findById(pId).get());
		List<Permission> permissionsList =  permissionService.findAll();
		model.addAttribute("entity", permission);
		model.addAttribute("permissionsList", permissionsList);
		return "userRoleDetail";
	}
	
	@RequestMapping(value = "/admin/userroles/updatePermissions", method = RequestMethod.POST)
	public String mapPermissionsToUserRole(Model model, @ModelAttribute("entity") UserRoleForm entity) {
		UserRole p = userRoleService.findById(entity.getId()).get();
		p.setPermissions(entity.getPermissions());
		userRoleService.save(p);
		return "redirect:/admin/userroles";
	}
	
	private String getLoggedInUserName(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}

		return principal.toString();
	}
	
	

}
