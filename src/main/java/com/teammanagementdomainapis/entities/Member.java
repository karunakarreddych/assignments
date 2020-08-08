package com.teammanagementdomainapis.entities;

import java.time.Instant;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Member {

	@Field("employee_id")
	private String employeeId;
	@Field("role")
	private String role;
	@Field("can_manage")
	private boolean canManage;
	@Field("createdAt")
	@JsonIgnore
	private Instant createdAt = Instant.now();
	@Field("updatedAt")
	@JsonIgnore
	private Instant updatedAt = Instant.now();

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isCanManage() {
		return canManage;
	}

	public void setCanManage(boolean canManage) {
		this.canManage = canManage;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Member [employeeId=" + employeeId + ", role=" + role + ", canManage=" + canManage + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}

	public Member(String employeeId, String role, boolean canManage, Instant createdAt, Instant updatedAt) {
		super();
		this.employeeId = employeeId;
		this.role = role;
		this.canManage = canManage;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

}