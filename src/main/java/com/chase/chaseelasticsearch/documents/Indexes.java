package com.chase.chaseelasticsearch.documents;

public class Indexes {
	
	public Indexes(String employeeIndex, String messageIndex, String teamIndex ) {
		this.employeeIndex = employeeIndex;
		this.teamIndex = teamIndex;
		this.messageIndex = messageIndex;
	}

	private String employeeIndex;
	private String messageIndex;
	private String teamIndex;
	
	
	
	public String getMessageIndex() {
		return messageIndex;
	}

	public void setMessageIndex(String messageIndex) {
		this.messageIndex = messageIndex;
	}

	public String getTeamIndex() {
		return teamIndex;
	}

	public void setTeamIndex(String teamIndex) {
		this.teamIndex = teamIndex;
	}

	public String getEmployeeIndex() {
		return employeeIndex;
	}

	public void setEmployeeIndex(String employeeIndex) {
		this.employeeIndex = employeeIndex;
	}
	
	
	
}
