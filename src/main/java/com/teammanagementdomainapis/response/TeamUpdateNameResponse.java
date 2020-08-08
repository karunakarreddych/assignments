package com.teammanagementdomainapis.response;

public class TeamUpdateNameResponse extends Response {
	private String teamName;
	boolean success;

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "TeamUpdateNameResponse [teamName=" + teamName + ", success=" + success + "]";
	}

}
