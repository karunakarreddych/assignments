package com.teammanagementdomainapis.request;

public class TeamModel {

	private String name;
	private String logo_url;
	private String tagline;
	private String chat_id;

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

	public String getChat_id() {
		return chat_id;
	}

	public void setChat_id(String chat_id) {
		this.chat_id = chat_id;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public TeamModel(String name, String logo_url, String tagline, String chat_id) {
		super();
		this.name = name;
		this.logo_url = logo_url;
		this.tagline = tagline;
		this.chat_id = chat_id;
	}

	@Override
	public String toString() {
		return "TeamModel [name=" + name + ", logo_url=" + logo_url + ", tagline=" + tagline + ", chat_id=" + chat_id
				+ "]";
	}

	public TeamModel() {
		super();
		// TODO Auto-generated constructor stub
	}

}
