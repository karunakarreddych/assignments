package com.teammanagementdomainapis.request;

public class EmployeeModel {
	private String name;
	private String photo_url;
	private String designation;
	private String chat_id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChat_id() {
		return chat_id;
	}

	public void setChat_id(String chat_id) {
		this.chat_id = chat_id;
	}

	public String getPhoto_url() {
		return photo_url;
	}

	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public EmployeeModel(String name, String photo_url, String designation, String chat_id) {
		super();
		this.name = name;
		this.photo_url = photo_url;
		this.designation = designation;
		this.chat_id = chat_id;
	}

	@Override
	public String toString() {
		return "EmployeeModel [name=" + name + ", photo_url=" + photo_url + ", designation=" + designation
				+ ", chat_id=" + chat_id + "]";
	}

	public EmployeeModel() {
		super();
		// TODO Auto-generated constructor stub
	}

}
