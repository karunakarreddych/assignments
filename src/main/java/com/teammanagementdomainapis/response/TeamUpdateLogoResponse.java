package com.teammanagementdomainapis.response;

public class TeamUpdateLogoResponse extends Response {
	private String logo_url;
	boolean success;

	public String getLogo_url() {
		return logo_url;
	}

	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public TeamUpdateLogoResponse(String logo_url, boolean success) {
		super();
		this.logo_url = logo_url;
		this.success = success;
	}

	public TeamUpdateLogoResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "TeamUpdateLogoResponse [logo_url=" + logo_url + ", success=" + success + "]";
	}

}
