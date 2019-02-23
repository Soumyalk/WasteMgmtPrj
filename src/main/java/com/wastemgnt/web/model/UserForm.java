package com.wastemgnt.web.model;

import java.util.ArrayList;
import java.util.List;

import com.wastemgnt.service.entity.StateCityConfig;
import com.wastemgnt.service.entity.User;
import com.wastemgnt.service.entity.UserRole;

public class UserForm {

	private String password;
	private String loginId;
	private String phone;
	private String firstName;
	private String lastName;
	private String email;
	private String gender;
	private String selectedRole;
	private boolean userEnabled;
	private List<UserRole> userRoles;
	private String selectedUserRole;
	private String entityName;
	private String description;
	private String address;
	private String state;
	private String city;
	private UserRole role;
	private StateCityConfig stateConfig;
	private String changedPwd;
	private String confirmPwd;

	public StateCityConfig getStateConfig() {
		return stateConfig;
	}

	public void setStateConfig(StateCityConfig stateConfig) {
		this.stateConfig = stateConfig;
	}

	public String getChangedPwd() {
		return changedPwd;
	}

	public void setChangedPwd(String changedPwd) {
		this.changedPwd = changedPwd;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	public UserForm() {
		super();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public UserForm(User user) {
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.loginId = user.getLoginId();
		this.userEnabled = user.isUserEnabled();
		this.phone = user.getPhone();
		this.email = user.getEmail();
		this.gender = user.getGender();
		this.userRoles = new ArrayList<>(user.getUserRoles());
		this.role = !user.getUserRoles().isEmpty() ? user.getUserRoles().get(0) : null;
		this.selectedRole = "INDIVIDUAL";
		if (null != user.getEntityType()) {
			this.address = user.getEntityType().getAddress();
			this.entityName = user.getEntityType().getEntityName();
			this.description = user.getEntityType().getEntityDescription();
			this.stateConfig = user.getEntityType().getStateConfig();
			this.selectedRole = "ENTITY";
		}
	}

	public String getSelectedUserRole() {
		return selectedUserRole;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public void setSelectedUserRole(String selectedUserRole) {
		this.selectedUserRole = selectedUserRole;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(String selectedRole) {
		this.selectedRole = selectedRole;
	}

	public boolean isUserEnabled() {
		return userEnabled;
	}

	public void setUserEnabled(boolean userEnabled) {
		this.userEnabled = userEnabled;
	}

}
