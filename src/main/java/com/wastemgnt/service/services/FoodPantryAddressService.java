package com.wastemgnt.service.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wastemgnt.service.entity.FoodPantryAddress;


public interface FoodPantryAddressService extends JpaRepository<FoodPantryAddress, Long> {
	
}
	