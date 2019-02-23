package com.wastemgnt.web.model;

import java.util.ArrayList;
import java.util.List;

import com.wastemgnt.service.entity.FoodPantryFoodItemLimit;

public class FoodPantryFoodItemLimitModel {

	private String foodName;

	private String address;

	private Long id;

	private Long maxAllowed;

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMaxAllowed() {
		return maxAllowed;
	}

	public void setMaxAllowed(Long maxAllowed) {
		this.maxAllowed = maxAllowed;
	}

	public FoodPantryFoodItemLimitModel(String foodName, String address, Long id, Long maxAllowed) {
		super();
		this.foodName = foodName;
		this.address = address;
		this.id = id;
		this.maxAllowed = maxAllowed;
	}

}
