package com.wastemgnt.service.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wastemgnt.web.model.OrderFoodItem;

@Entity
@Table(name = "order_has_food_items")
public class OrderHasFoodItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "value")
	private Double value;

	@Column(name = "order_id")
	private Long order;

	@Column(name = "food_item")
	private Long foodName;

	@Column(name = "created_on")
	private Date createdOn;

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

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	public Long getFoodName() {
		return foodName;
	}

	public void setFoodName(Long foodName) {
		this.foodName = foodName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, value, foodName, order);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof OrderHasFoodItem) {
			OrderHasFoodItem ob = (OrderHasFoodItem) obj;
			return ob.getOrder().equals(this.order) && ob.getFoodName().equals(this.foodName)
					&& ob.getValue().equals(this.value);
		}
		return false;
	}

	public OrderHasFoodItem() {
		super();
		this.createdOn = new Date();
	}

	public OrderHasFoodItem(OrderFoodItem item) {
		this.id = item.getId();
		this.order = item.getOrder().getId();
		this.foodName = item.getFoodName().getId();
		this.value = item.getValue();
		this.createdOn = null == item.getCreatedOn() ? new Date() : item.getCreatedOn();
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
