package com.wastemgnt.service.entity;

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
@Table(name = "user_entity")
public class EntityType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "address")
	private String address;

	@Column(name = "entity_name")
	private String entityName;

	@Column(name = "description")
	private String entityDescription;

	@OneToOne(cascade = CascadeType.DETACH)
	@JoinTable(name = "user_entity_has_state", joinColumns = {
			@JoinColumn(name = "user_entity") }, inverseJoinColumns = { @JoinColumn(name = "state_city_config") })
	private StateCityConfig stateConfig;

	public StateCityConfig getStateConfig() {
		return stateConfig;
	}

	public void setStateConfig(StateCityConfig stateConfig) {
		this.stateConfig = stateConfig;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEntityDescription() {
		return entityDescription;
	}

	public void setEntityDescription(String entityDescription) {
		this.entityDescription = entityDescription;
	}

}
