package com.wastemgnt.web.model;

import java.util.Date;

import com.wastemgnt.service.entity.FoodName;
import com.wastemgnt.service.entity.Order;
import com.wastemgnt.service.entity.OrderHasFoodItem;

public class OrderFoodItem {

	private Long id;

	private Double value;

	private Order order;

	private FoodName foodName;

	private Date createdOn;
	
	private OrderHasFoodItem item;

	public OrderFoodItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderFoodItem(OrderHasFoodItem item, Order o, FoodName fName) {
		this.id = item.getId();
		this.order = o;
		this.foodName = fName;
		this.value = item.getValue();
		this.createdOn = null == item.getCreatedOn() ? new Date() : item.getCreatedOn();
		this.item = item;
	}

	public OrderHasFoodItem getItem() {
		return item;
	}

	public void setItem(OrderHasFoodItem item) {
		this.item = item;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public FoodName getFoodName() {
		return foodName;
	}

	public void setFoodName(FoodName foodName) {
		this.foodName = foodName;
	}

}
