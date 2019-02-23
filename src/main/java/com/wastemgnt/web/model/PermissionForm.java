package com.wastemgnt.web.model;

import com.wastemgnt.service.entity.Permission;

public class PermissionForm {

	
	private long id;
	
	private String name;
	
	private String desc;
	
	

	public PermissionForm() {
	}

	public PermissionForm(Permission p) {
		this.id = p.getId();
		this.name = p.getPermission();
		this.desc = p.getDescription();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
}
