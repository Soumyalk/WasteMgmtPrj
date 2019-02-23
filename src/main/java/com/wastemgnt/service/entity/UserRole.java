package com.wastemgnt.service.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "role")
	private String roleName;
	@Column(name = "description")
	private String description;
	@OneToMany(cascade = CascadeType.DETACH)
   	@JoinTable(name = "role_has_permission",   	
   	joinColumns = { @JoinColumn(name = "user_role") }, 
   	inverseJoinColumns = { @JoinColumn(name = "permission_id")})
	private List<Permission> permissions;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, roleName, description);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof UserRole) {
			UserRole r = (UserRole)obj;
			return this.roleName.equals(r.getRoleName());
		}
		return false;
	}

	@Override
	public String toString() {
		return roleName;
	}

}
