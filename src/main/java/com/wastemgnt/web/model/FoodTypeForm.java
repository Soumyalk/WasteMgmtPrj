package com.wastemgnt.web.model;

import com.wastemgnt.service.entity.FoodType;

public class FoodTypeForm {

	private Long id;
	private String name;
	private String qType;

	public FoodTypeForm() {
	}

	public FoodTypeForm(FoodType type) {
		this.id = type.getId();
		this.name = type.getName();
		this.qType = type.getqType();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getqType() {
		return qType;
	}

	public void setqType(String qType) {
		this.qType = qType;
	}

}
