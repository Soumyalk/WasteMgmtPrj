package com.wastemgnt.service.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "food_order")
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "updated_by")
	private String updatedBy;
	@Column(name = "created_on")
	private Date createdOn;
	@Column(name = "updated_on")
	private Date updatedOn;
	@Column(name = "pickup_location")
	private String pickupLocation;
	@Column(name = "status")
	private String status;
	@Column(name = "description")
	private String description;
	@Column(name = "assignee")
	private Long assignee;

	@OneToOne(cascade = CascadeType.DETACH)
	@JoinTable(name = "order_has_address", joinColumns = { @JoinColumn(name = "food_order") }, inverseJoinColumns = {
			@JoinColumn(name = "address") })
	private FoodPantryAddress address;
	
	@OneToOne(cascade = CascadeType.DETACH)
	@JoinTable(name = "order_addr_has_state", joinColumns = { @JoinColumn(name = "food_order") }, inverseJoinColumns = {
			@JoinColumn(name = "state_city_config") })
	private StateCityConfig stateConfig;
	

	public StateCityConfig getStateConfig() {
		return stateConfig;
	}

	public void setStateConfig(StateCityConfig stateConfig) {
		this.stateConfig = stateConfig;
	}

	public FoodPantryAddress getAddress() {
		return address;
	}

	public void setAddress(FoodPantryAddress address) {
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public Long getAssignee() {
		return assignee;
	}

	public void setAssignee(Long assignee) {
		this.assignee = assignee;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
