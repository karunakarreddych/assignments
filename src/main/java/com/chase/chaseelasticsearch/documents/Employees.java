package com.chase.chaseelasticsearch.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "#{index.getEmployeeIndex()}")
public class Employees {
	
	@Id
	@Field("_id")
	private String id;
	
	@Field("document.email")
	private String email;
	@Field("document.name")
	private String name;
	@Field("document.username")
	private String username;
	@Field("document.active")
	private String active;
	@Field("document.org_id")
	private String organizationId;
	@Field("document.photo_url")
	private String photoUrl;
	@Field("document.about")
	private String about;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	
	
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	@Override
	public String toString() {
		return "Employees [id=" + id + ", email=" + email + ", name=" + name + ", username=" + username + ", active="
				+ active + ", organizationId=" + organizationId + "]";
	}	
	
	
	

	
	
	
}
