package com.chase.chaseelasticsearch.dto;

import com.chase.chaseelasticsearch.documents.Employees;
import com.chase.chaseelasticsearch.documents.Messages;
import com.chase.chaseelasticsearch.documents.Team;

public class GlobalSearchDTO {

	private String index;
	private Messages messages;
	private Team teams;
	private Employees employees;
	
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public Messages getMessages() {
		return messages;
	}
	public void setMessages(Messages messages) {
		this.messages = messages;
	}
	public Team getTeams() {
		return teams;
	}
	public void setTeams(Team teams) {
		this.teams = teams;
	}
	public Employees getEmployees() {
		return employees;
	}
	public void setEmployees(Employees employees) {
		this.employees = employees;
	}
	
	
	
	
	
}
