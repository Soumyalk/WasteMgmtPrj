package com.wastemgnt.web.model;

import com.wastemgnt.service.entity.FoodName;
import com.wastemgnt.service.entity.FoodType;

public class FoodNameForm {

	private Long id;

	private String foodName;

	private FoodType foodType;
	
	private Long limit;

	public FoodNameForm(FoodName name) {
		this.id = name.getId();
		this.foodName = name.getFoodName();
		this.foodType = name.getFoodType();
		this.limit = name.getLimit();
	}

	public FoodNameForm() {
	}

	public Long getLimit() {
		return limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public FoodType getFoodType() {
		return foodType;
	}

	public void setFoodType(FoodType foodType) {
		this.foodType = foodType;
	}

}
