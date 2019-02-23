package com.wastemgnt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wastemgnt.service.entity.Permission;
import com.wastemgnt.service.entity.UserRole;
import com.wastemgnt.service.services.PermissionService;
import com.wastemgnt.service.services.UserRoleService;
import com.wastemgnt.web.model.PermissionForm;
import com.wastemgnt.web.utils.Utilities;

@Controller("")
public class PermissionsController {
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private UserRoleService roleService;
	
	@RequestMapping(value = "/admin/permissions", method = RequestMethod.GET)
	public String showPermissions(Model model) {

		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		List<Permission> permissions = permissionService.findAll();
		model.addAttribute("permissionsList", permissions);
		return "permissions";
	}
	
	@RequestMapping(value = "/admin/permissions/create", method = RequestMethod.GET)
	public String createPermission(Model model) {

		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		PermissionForm entity = new PermissionForm();
		model.addAttribute("entity", entity);
		return "createPermission";
	}
	
	@RequestMapping(value = "/admin/permissions/save", method = RequestMethod.POST)
	public String savePermission(Model model, @ModelAttribute("entity") PermissionForm entity) {
		Permission p = new Permission();
		p.setDescription(entity.getDesc());
		p.setPermission(entity.getName());
		permissionService.save(p);
		return "redirect:/admin/permissions";
	}
	

	@RequestMapping(value = "/admin/permissions/edit", method = RequestMethod.GET)
	public String editPermission(Model model, @RequestParam("pId") Long pId) {

		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		PermissionForm permission = new PermissionForm(permissionService.findById(pId).get());
		model.addAttribute("entity", permission);
		return "permission";
	}
	
	@RequestMapping(value = "/admin/permissions/delete", method = RequestMethod.GET)
	public String deletePermission(Model model, @RequestParam("pId") Long pId) {

		Permission permission = permissionService.findById(pId).get();
		List<UserRole> roles = roleService.findAll();
		for(UserRole role : roles) {
			if(role.getPermissions().contains(permission)) {
				role.getPermissions().remove(permission);
				roleService.save(role);
			}
		}
		permissionService.delete(permission);
		return "redirect:/admin/permissions";
	}
	
	@RequestMapping(value = "/admin/permissions/update", method = RequestMethod.POST)
	public String updatePermission(Model model, @ModelAttribute("entity") PermissionForm entity) {
		Permission p = new Permission();
		p.setId(entity.getId());
		p.setDescription(entity.getDesc());
		p.setPermission(entity.getName());
		permissionService.save(p);
		return "redirect:/admin/permissions";
	}
	

}
