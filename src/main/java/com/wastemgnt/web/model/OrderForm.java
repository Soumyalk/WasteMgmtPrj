package com.wastemgnt.web.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wastemgnt.service.entity.FoodName;
import com.wastemgnt.service.entity.FoodPantryAddress;
import com.wastemgnt.service.entity.Order;
import com.wastemgnt.service.entity.OrderPantryHasFoodItem;
import com.wastemgnt.service.entity.StateCityConfig;
import com.wastemgnt.service.entity.User;

//Create Food Order Form
public class OrderForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String createdBy;
	private String updatedBy;
	private Date createdOn;
	private Date updatedOn;
	private String pickupLocation;
	private String status;
	private String description;
	private OrderFoodItem orderFoodItem;
	private User Assignee;

	//Will have list of food Item
	private List<OrderFoodItem> foodItems;
	
	private FoodPantryAddress address;
	private StateCityConfig stateConfig;
	
	//Stores the list of Order-Pantry-Item List
	private List<OrderPantryHasFoodItem> orderHasFoodItemList;
	private OrderPantryHasFoodItem orderPantryHasFoodItem;
	private FoodName fName;

	public OrderForm() {
		super();
		foodItems = new ArrayList<>();
		orderFoodItem = new OrderFoodItem();
		this.address = new FoodPantryAddress();
		orderPantryHasFoodItem = new OrderPantryHasFoodItem();
	}

	public FoodName getfName() {
		return fName;
	}

	public void setfName(FoodName fName) {
		this.fName = fName;
	}

	public OrderPantryHasFoodItem getOrderPantryHasFoodItem() {
		return orderPantryHasFoodItem;
	}

	public void setOrderPantryHasFoodItem(OrderPantryHasFoodItem orderPantryHasFoodItem) {
		this.orderPantryHasFoodItem = orderPantryHasFoodItem;
	}

	public List<OrderPantryHasFoodItem> getOrderHasFoodItemList() {
		return orderHasFoodItemList;
	}

	public void setOrderHasFoodItemList(List<OrderPantryHasFoodItem> orderHasFoodItemList) {
		this.orderHasFoodItemList = orderHasFoodItemList;
	}

	public StateCityConfig getStateConfig() {
		return stateConfig;
	}

	public void setStateConfig(StateCityConfig stateConfig) {
		this.stateConfig = stateConfig;
	}

	public User getAssignee() {
		return Assignee;
	}

	public void setAssignee(User assignee) {
		Assignee = assignee;
	}

	public void setOrderFoodItem(OrderFoodItem orderFoodItem) {
		this.orderFoodItem = orderFoodItem;
	}

	public OrderFoodItem getOrderFoodItem() {
		return orderFoodItem;
	}

	public void setOrderFoodItems(OrderFoodItem orderFoodItem) {
		this.orderFoodItem = orderFoodItem;
	}

	public OrderForm(Order o, List<OrderFoodItem> foodItems) {
		this.id = o.getId();
		this.createdBy = o.getCreatedBy();
		this.updatedBy = o.getUpdatedBy();
		this.createdOn = o.getCreatedOn();
		this.updatedOn = o.getUpdatedOn();
		this.pickupLocation = o.getPickupLocation();
		this.description = o.getDescription();
		this.status = o.getStatus();
		this.foodItems = foodItems;
		orderFoodItem = new OrderFoodItem();
		this.address = o.getAddress();
		this.stateConfig = o.getStateConfig();
		orderPantryHasFoodItem = new OrderPantryHasFoodItem();
	}

	public OrderForm(Order o, User u) {
		this.id = o.getId();
		this.createdBy = o.getCreatedBy();
		this.updatedBy = o.getUpdatedBy();
		this.createdOn = o.getCreatedOn();
		this.updatedOn = o.getUpdatedOn();
		this.pickupLocation = o.getPickupLocation();
		this.description = o.getDescription();
		this.status = o.getStatus();
		this.Assignee = u;
		this.foodItems = new ArrayList<>();
		orderFoodItem = new OrderFoodItem();
		this.address = o.getAddress();
		this.stateConfig = o.getStateConfig();
		orderPantryHasFoodItem = new OrderPantryHasFoodItem();
	}

	public FoodPantryAddress getAddress() {
		return address;
	}

	public void setAddress(FoodPantryAddress address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public List<OrderFoodItem> getFoodItems() {
		return foodItems;
	}

	public void setFoodItems(List<OrderFoodItem> foodItems) {
		this.foodItems = foodItems;
	}

}
