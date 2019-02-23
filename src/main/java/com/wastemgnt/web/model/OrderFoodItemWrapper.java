package com.wastemgnt.web.model;

import java.io.Serializable;

import com.wastemgnt.service.entity.FoodName;

public class OrderFoodItemWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String value;

	private FoodName foodName;

	
	public OrderFoodItemWrapper() {
		super();
	}

	public OrderFoodItemWrapper(String value, FoodName foodName) {
		super();
		this.value = value;
		this.foodName = foodName;
	}


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public FoodName getFoodName() {
		return foodName;
	}

	public void setFoodName(FoodName foodName) {
		this.foodName = foodName;
	}

}
