package com.wastemgnt.service.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wastemgnt.service.entity.FoodType;

public interface FoodTypeService extends JpaRepository<FoodType, Long> {

}
