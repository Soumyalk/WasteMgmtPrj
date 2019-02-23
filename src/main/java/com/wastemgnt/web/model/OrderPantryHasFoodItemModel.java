package com.wastemgnt.web.model;

public class OrderPantryHasFoodItemModel {

	private Long id;

	private String foodName;

	private String address;

	private Long assignedQt;

	private Long availableQty;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getAssignedQt() {
		return assignedQt;
	}

	public void setAssignedQt(Long assignedQt) {
		this.assignedQt = assignedQt;
	}

	public Long getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(Long availableQty) {
		this.availableQty = availableQty;
	}

	public OrderPantryHasFoodItemModel(Long id, String foodName, String address, Long assignedQt, Long availableQty) {
		super();
		this.id = id;
		this.foodName = foodName;
		this.address = address;
		this.assignedQt = assignedQt;
		this.availableQty = availableQty;
	}

}
