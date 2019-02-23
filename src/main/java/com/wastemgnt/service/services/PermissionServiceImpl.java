package com.wastemgnt.service.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wastemgnt.service.entity.Permission;

@Service
@Transactional
public class PermissionServiceImpl {
	
	@Autowired
	private PermissionService permissionService;
	
	public Permission getByName(String name) {
		return permissionService.findByName(name);
	}

}
