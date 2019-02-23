package com.wastemgnt.service.entity;

import java.io.Serializable;
import java.util.Objects;

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
@Table(name = "food_name")
public class FoodName implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "food_name")
	private String foodName;

	@Column(name = "qty_limit")
	private Long limit;

	//Child table configuration.
	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "food_name_has_food_type", joinColumns = {
			@JoinColumn(name = "food_name") }, inverseJoinColumns = { @JoinColumn(name = "food_type") })
	private FoodType foodType;

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

	public Long getLimit() {
		return limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	public void setFoodType(FoodType foodType) {
		this.foodType = foodType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, foodName, foodType);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FoodName) {
			FoodName o = (FoodName) obj;
			return o.getFoodName().equals(this.foodName) && o.getFoodType().equals(this.getFoodType());
		}
		return false;
	}

}
