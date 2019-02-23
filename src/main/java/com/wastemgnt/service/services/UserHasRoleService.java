package com.wastemgnt.service.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wastemgnt.service.entity.UserHasRole;

@Repository("userHasRoleService")
public interface UserHasRoleService extends JpaRepository<UserHasRole, Long> {
	
	@Query("select u from UserHasRole u where u.userId = :userId")
	List<UserHasRole> getByUserId(@Param("userId") Long userId);

}
