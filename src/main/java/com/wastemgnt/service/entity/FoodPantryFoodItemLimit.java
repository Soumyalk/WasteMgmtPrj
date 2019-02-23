package com.wastemgnt.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pantry_food_item_has_limit")
public class FoodPantryFoodItemLimit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "address")
	private Long fooddPantryAddress;

	@Column(name = "food_name")
	private Long foodItem;

	@Column(name = "max_allowed_food")
	private Long maxAllowedFood;
	
	@Column(name = "available_limit")
	private Long availableLimit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAvailableLimit() {
		return availableLimit;
	}

	public void setAvailableLimit(Long availableLimit) {
		this.availableLimit = availableLimit;
	}

	public Long getFooddPantryAddress() {
		return fooddPantryAddress;
	}

	public void setFooddPantryAddress(Long fooddPantryAddress) {
		this.fooddPantryAddress = fooddPantryAddress;
	}

	public Long getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(Long foodItem) {
		this.foodItem = foodItem;
	}

	public Long getMaxAllowedFood() {
		return maxAllowedFood;
	}

	public void setMaxAllowedFood(Long maxAllowedFood) {
		this.maxAllowedFood = maxAllowedFood;
	}

}
