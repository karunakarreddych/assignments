package com.teammanagementdomainapis.response;

public class EmployeeUpdatePhotoResponse extends Response {
	private String photo_url;

	public String getPhoto_url() {
		return photo_url;
	}

	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}

	public EmployeeUpdatePhotoResponse(String photo_url) {
		super();
		this.photo_url = photo_url;

	}

	@Override
	public String toString() {
		return "EmployeeUpdatePhotoResponse [photo_url=" + photo_url + "]";
	}

	public EmployeeUpdatePhotoResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

}
