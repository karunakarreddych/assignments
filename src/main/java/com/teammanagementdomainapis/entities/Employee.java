package com.teammanagementdomainapis.entities;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "employees")
public class Employee {
	@Id
	private String id;
	@Field("tenant_id")
	private String tenantId;
	@Field("organization_id")
	private String organizationId;
	@Field("name")
	private String name;
	@Field("photo_url")
	private String photoUrl;
	@Field("email")
	private String email;
	@Field("phone_no")
	private String phoneNo;
	@Field("designation")
	private String designation;
	@Field("employee_no")
	private String employeeNo;
	@Field("chat_id")
	private String chatId;
	@Field("username")
	private String username;
	@Field("status")
	private boolean status;
	@Field("createdAt")
	@JsonIgnore
	private Instant createdAt = Instant.now();
	@Field("updatedAt")
	@JsonIgnore
	private Instant updatedAt = Instant.now();

	public String getId() {
		return id;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
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

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public Employee(String id, String tenantId, String organizationId, String name, String photoUrl, String email,
			String phoneNo, String designation, String employeeNo, String chatId, String username, boolean status,
			Instant createdAt, Instant updatedAt) {
		super();
		this.id = id;
		this.tenantId = tenantId;
		this.organizationId = organizationId;
		this.name = name;
		this.photoUrl = photoUrl;
		this.email = email;
		this.phoneNo = phoneNo;
		this.designation = designation;
		this.employeeNo = employeeNo;
		this.chatId = chatId;
		this.username = username;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", tenantId=" + tenantId + ", organizationId=" + organizationId + ", name=" + name
				+ ", photoUrl=" + photoUrl + ", email=" + email + ", phoneNo=" + phoneNo + ", designation="
				+ designation + ", employeeNo=" + employeeNo + ", chatId=" + chatId + ", username=" + username
				+ ", status=" + status + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

}