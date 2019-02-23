package com.wastemgnt.service.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wastemgnt.service.entity.User;

@Repository("userService")
public interface UserService extends JpaRepository<User, Long> {
	
	@Query("select u from User u where u.loginId= :username")
    User findByLoginId(@Param("username") String username);
}
