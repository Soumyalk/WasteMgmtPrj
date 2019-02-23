package com.wastemgnt.service.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wastemgnt.service.entity.Permission;

@Repository("permissionService")
public interface PermissionService extends  JpaRepository<Permission, Long> {
	
	@Query("select p from Permission p where p.permission = :name")
	Permission findByName(@Param("name") String name);
	
	@Query("select p from Permission p")
	List<Permission> findAll();

}
