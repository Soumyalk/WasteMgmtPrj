package com.wastemgnt.service.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wastemgnt.service.entity.FoodPantryFoodItemLimit;

public interface FoodPantryFoodItemLimitService extends JpaRepository<FoodPantryFoodItemLimit, Long> {

	@Query("select u from FoodPantryFoodItemLimit u where u.fooddPantryAddress= :address and u.foodItem = :foodName")
	public FoodPantryFoodItemLimit getByAddressAndFoodName(@Param("address") Long address,
			@Param("foodName") Long foodName);	

}
