package com.teammanagementdomainapis.request;

public class EmployeeRequest {
	private String photo_url;

	public String getPhoto_url() {
		return photo_url;
	}

	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}

	@Override
	public String toString() {
		return "EmployeeRequest [photo_url=" + photo_url + "]";
	}

}
