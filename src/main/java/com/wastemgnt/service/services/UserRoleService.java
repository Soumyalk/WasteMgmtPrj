package com.wastemgnt.service.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wastemgnt.service.entity.UserRole;

@Repository("userRoleService")
public interface UserRoleService extends JpaRepository<UserRole, Long> {

	@Query("select p from UserRole p where p.roleName = :name")
	UserRole findByName(@Param("name") String name);

}
