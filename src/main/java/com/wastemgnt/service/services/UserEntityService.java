package com.wastemgnt.service.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wastemgnt.service.entity.EntityType;

public interface UserEntityService extends JpaRepository<EntityType, Long> {

}
