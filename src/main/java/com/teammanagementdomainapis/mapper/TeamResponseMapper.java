package com.teammanagementdomainapis.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.teammanagementdomainapis.entities.Employee;
import com.teammanagementdomainapis.entities.Team;
import com.teammanagementdomainapis.request.EmployeeModel;
import com.teammanagementdomainapis.request.TeamModel;

@Service
public class TeamResponseMapper {
	public List<TeamModel> mapListOfTeamToTeamResponse(Page<Team> pageResponse) {
		List<Team> teams = pageResponse.getContent();
		List<TeamModel> listOfTeamModel = new ArrayList<TeamModel>();
	teams.stream().forEach(team -> listOfTeamModel.add(new TeamModel(team.getName(), team.getLogo_url(), team.getTagline(), team.getChat_id())));
	return listOfTeamModel;
	}
	public List<EmployeeModel> mapListOfEmployeesToEmployeeResponse(Page<Employee> employeePageResponse) {
		List<Employee> employees = employeePageResponse.getContent();
		List<EmployeeModel> listOfEmployeeModel = new ArrayList<EmployeeModel>();
		employees.forEach(employee -> listOfEmployeeModel.add(new EmployeeModel(employee.getName(),
				employee.getPhotoUrl(), employee.getDesignation(), employee.getChatId())));
		return listOfEmployeeModel;
	}

}
