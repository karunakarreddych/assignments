package com.teammanagementdomainapis.request;

public class TeamNameRequest {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "TeamNameRequest [name=" + name + "]";
	}

	public TeamNameRequest(String name) {
		super();
		this.name = name;
	}

	public TeamNameRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

}
