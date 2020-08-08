package com.teammanagementdomainapis.entities;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "teams")
public class Team {
	@Id
	private String id;
	@Field("organization_id")
	private String organizationId;
	@Field("tenant_id")
	private String tenantId;
	@Field("name")
	private String name;
	@Field("logo_url")
	private String logo_url;
	@Field("tagline")
	private String tagline;
	@Field("chat_id")
	private String chat_id;
	@Field("createdAt")
	@JsonIgnore
	private Instant createdAt = Instant.now();
	@Field("updatedAt")
	@JsonIgnore
	private Instant updatedAt = Instant.now();
	private List<Member> members;

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo_url() {
		return logo_url;
	}

	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public String getChat_id() {
		return chat_id;
	}

	public void setChat_id(String chat_id) {
		this.chat_id = chat_id;
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

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public String getId() {
		return id;
	}

	public Team(String id, String organizationId, String tenantId, String name, String logo_url, String tagline,
			String chat_id, Instant createdAt, Instant updatedAt, List<Member> members) {
		super();
		this.id = id;
		this.organizationId = organizationId;
		this.tenantId = tenantId;
		this.name = name;
		this.logo_url = logo_url;
		this.tagline = tagline;
		this.chat_id = chat_id;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.members = members;
	}

	public Team() {
		super();
		// TODO Auto-generated constructor stub
	}

}
