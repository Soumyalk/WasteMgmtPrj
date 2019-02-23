package com.wastemgnt.web.model;

import java.util.List;

import com.wastemgnt.service.entity.Permission;
import com.wastemgnt.service.entity.UserRole;

public class UserRoleForm {

	private long id;

	private String roleName;

	private String desc;

	private List<Permission> permissions;
	
	

	public UserRoleForm() {
	}

	public UserRoleForm(UserRole role) {
		super();
		this.id = role.getId();
		this.roleName = role.getRoleName();
		this.desc = role.getDescription();
		this.permissions = role.getPermissions();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

}
