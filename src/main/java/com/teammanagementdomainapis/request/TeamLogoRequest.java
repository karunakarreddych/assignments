package com.teammanagementdomainapis.request;

public class TeamLogoRequest {
	private String logo_url;

	public String getLogo_url() {
		return logo_url;
	}

	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}

	@Override
	public String toString() {
		return "TeamLogoRequest [logo_url=" + logo_url + "]";
	}

	public TeamLogoRequest(String logo_url) {
		super();
		this.logo_url = logo_url;
	}

	public TeamLogoRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
}
