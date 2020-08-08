package com.teammanagementdomainapis.response;

import java.util.List;

import com.teammanagementdomainapis.request.TeamModel;

public class TeamResponse extends Response {
	List<TeamModel> teams;

	Integer currentPage;
	Integer limit;

	public List<TeamModel> getTeams() {
		return teams;
	}

	public void setTeams(List<TeamModel> teams) {
		this.teams = teams;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "TeamResponse [teams=" + teams + ", currentPage=" + currentPage + ", limit=" + limit + "]";
	}

	public TeamResponse(List<TeamModel> teams, Integer currentPage, Integer limit) {
		super();
		this.teams = teams;
		this.currentPage = currentPage;
		this.limit = limit;
	}

	public TeamResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

}
