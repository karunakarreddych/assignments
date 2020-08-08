package com.teammanagementdomainapis.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.teammanagementdomainapis.entities.Employee;
import com.teammanagementdomainapis.entities.Member;
import com.teammanagementdomainapis.entities.Team;
import com.teammanagementdomainapis.exceptions.InvalidArgumentException;
import com.teammanagementdomainapis.exceptions.TeamNotFoundException;
import com.teammanagementdomainapis.repositories.EmployeeRepository;
import com.teammanagementdomainapis.repositories.TeamRepository;
import com.teammanagementdomainapis.response.TeamResponse;
import com.teammanagementdomainapis.util.Constants;

@Service
public class TeamService {
	Logger logger = LoggerFactory.getLogger(TeamService.class);

	@Autowired
	TeamRepository teamRepository;
	@Autowired
	EmployeeRepository employeeRepository;

	public List<Team> getListOfTeams() {
		return teamRepository.findAll();
	}

	public Page<Team> getListOfTeamsByEmployee(String employeeId, Integer currentPage, Integer limit) {
		TeamResponse teamResponse = new TeamResponse();
		if (currentPage == null) {
			teamResponse.setCurrentPage(Constants.DEFAULT_PAGE_NUMBER);
		} else if (currentPage < 0) {
			logger.error("you have entered invalid input");
			throw new InvalidArgumentException("Please Pass the valid Page no");
		} else {
			teamResponse.setCurrentPage(currentPage);
		}

		if (limit == null) {
			teamResponse.setLimit(Constants.DEFAULT_LIMIT);
		} else if (limit <= 0) {
			logger.error("you have entered invalid input");
			throw new InvalidArgumentException("Please Pass the valid Limit");
		} else {
			teamResponse.setLimit(limit);
		}
		Page<Team> listOfTeam = null;
		if (employeeId == null) {
			logger.error("Team Details are not found" + employeeId);
			throw new TeamNotFoundException("Team Details are Not Found " + employeeId);
		} else {
			listOfTeam = teamRepository.findByEmployeeId(employeeId,
					PageRequest.of(teamResponse.getCurrentPage(), teamResponse.getLimit()));
		}
		return listOfTeam;

	}

	public Page<Employee> getListOfEmployees(String teamId, Integer currentPage, Integer limit) {
		TeamResponse teamResponse = new TeamResponse();
		if (currentPage == null) {
			teamResponse.setCurrentPage(Constants.DEFAULT_PAGE_NUMBER);
		} else if (currentPage < 0) {
			logger.error("you have entered invalid input");
			throw new InvalidArgumentException("Please Pass the valid Page no");
		} else {
			teamResponse.setCurrentPage(currentPage);
		}
		if (limit == null) {
			teamResponse.setLimit(Constants.DEFAULT_LIMIT);
		} else if (limit <= 0) {
			logger.error("you have entered invalid input");
			throw new InvalidArgumentException("Please Pass the valid Limit");
		} else {
			teamResponse.setLimit(limit);
		}
		List<Employee> employees = null;
		Page<Employee> pageEmployee = null;
		if (teamId == null) {
			logger.error("Employee Not Found " + teamId);
			throw new TeamNotFoundException("Team Not Found  " + teamId);
		} else {
			Team team = teamRepository.findTeamById(teamId);
			if (team != null) {
				List<String> employeeIds = new ArrayList<String>();
				List<Member> members = team.getMembers();
				long totalCount = members.size();
				Member[] membersArr = members.toArray(new Member[members.size()]);
				for (int i = (teamResponse.getLimit() * (teamResponse.getCurrentPage() + 1)) - 1; i >= teamResponse
						.getCurrentPage() * teamResponse.getLimit(); i--) {

					if (i < totalCount) {

						employeeIds.add(membersArr[i].getEmployeeId());
					}
				}
				employees = employeeRepository.findAllById(employeeIds);
				pageEmployee = new PageImpl<Employee>(employees);
			} else {
				logger.error("Please pass the valid Team Id " + teamId);
				throw new InvalidArgumentException("Please pass the valid Team Id  :  " + teamId);
			}
		}

		return pageEmployee;
	}

}
