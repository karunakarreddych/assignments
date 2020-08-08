package com.teammanagementdomainapis.v1.controller;

import java.time.Instant;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teammanagementdomainapis.entities.Employee;
import com.teammanagementdomainapis.entities.Team;
import com.teammanagementdomainapis.exceptions.InvalidArgumentException;
import com.teammanagementdomainapis.mapper.TeamResponseMapper;
import com.teammanagementdomainapis.repositories.TeamRepository;
import com.teammanagementdomainapis.request.EmployeeModel;
import com.teammanagementdomainapis.request.TeamLogoRequest;
import com.teammanagementdomainapis.request.TeamModel;
import com.teammanagementdomainapis.request.TeamNameRequest;
import com.teammanagementdomainapis.response.TeamUpdateLogoResponse;
import com.teammanagementdomainapis.response.TeamUpdateNameResponse;
import com.teammanagementdomainapis.service.EmployeeService;
import com.teammanagementdomainapis.service.TeamService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/teams-management")
public class TeamController {

	private Logger logger = LoggerFactory.getLogger(TeamController.class);
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private TeamService teamService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	private TeamResponseMapper teamResponseMapper;

	@PostMapping(value = "/teams", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("create team")
	public String createTeam(@RequestBody Team team) {
		teamRepository.save(team);
		return "Team Created successfully...";
	}

	@PutMapping(value = "/teams/{teamId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation("update teamName by teamID")
	public ResponseEntity<TeamUpdateNameResponse> updateTeamNameById(@PathVariable(name = "teamId") String teamId,
			@RequestBody TeamNameRequest request) {
		TeamUpdateNameResponse updatedTeamName = new TeamUpdateNameResponse();
		Team team = teamRepository.findTeamById(teamId);
		if (team != null) {
			team.setName(request.getName());
			team.setUpdatedAt(Instant.now());
			team = teamRepository.save(team);
			updatedTeamName.setTeamName(team.getName());
			updatedTeamName.setMessage("Team Name Updated Successfully!!!");

		} else {
			logger.error("Please Pass the valid TeamId" + teamId);
			throw new InvalidArgumentException("Please pass the valid TeamId");
		}

		return ResponseEntity.status(HttpStatus.OK).body(updatedTeamName);

	}

	@PutMapping(value = "/teams/{teamId}/logo", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation("update teamLogo by teamId")
	public ResponseEntity<TeamUpdateLogoResponse> updateTeamLogo(@PathVariable(name = "teamId") String teamId,
			@RequestBody TeamLogoRequest request) {
		TeamUpdateLogoResponse updatedLogo = new TeamUpdateLogoResponse();
		Team team = teamRepository.findTeamById(teamId);
		if (team != null) {
			team.setLogo_url(request.getLogo_url());
			team.setUpdatedAt(Instant.now());
			team = teamRepository.save(team);
			updatedLogo.setLogo_url(team.getLogo_url());
			updatedLogo.setMessage("Logo Updated Successfully!!!");

		} else {
			logger.error("Please Pass the valid TeamId"  + teamId);
			throw new InvalidArgumentException("Please Pass the valid TeamId" );
		}

		return ResponseEntity.status(HttpStatus.OK).body(updatedLogo);

	}

	@GetMapping(value = "/employees/{employeeId}/teams", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("get teams for the particular employeeId")
	public ResponseEntity<List<TeamModel>> getTeamListForEmployee(HttpServletResponse responseHeader,
			@PathVariable(name = "employeeId") String employeeId,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "limit", required = false) Integer limit) {
		Page<Team> pageResponse = teamService.getListOfTeamsByEmployee(employeeId, page, limit);
		logger.info("total_count" + pageResponse.getTotalElements());
		logger.info("total_pages" + pageResponse.getTotalPages());
		logger.info("current_page" + pageResponse.getNumber()+1);
		logger.info("limit" + pageResponse.getNumberOfElements());
		responseHeader.addIntHeader("total_count", (int) (pageResponse.getTotalElements()));
		responseHeader.addIntHeader("total_pages", pageResponse.getTotalPages());
		responseHeader.addIntHeader("current_page", pageResponse.getNumber());
		responseHeader.addIntHeader("limit", pageResponse.getNumberOfElements());

		return ResponseEntity.status(HttpStatus.OK).body(teamResponseMapper.mapListOfTeamToTeamResponse(pageResponse));

	}

	@GetMapping(value = "/teams/{teamId}/members", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation("get employees for the particular teamId")
	public ResponseEntity<List<EmployeeModel>> getEmployees(HttpServletResponse responseHeader,
			@PathVariable(name = "teamId") String teamId,
			@RequestParam(name = "currentPage", required = false) Integer page,
			@RequestParam(name = "limit", required = false) Integer limit) {
		Page<Employee> employeePageResponse = teamService.getListOfEmployees(teamId, page, limit);
		logger.info("total_count" + employeePageResponse.getNumberOfElements());
		logger.info("limit" + employeePageResponse.getSize());
		logger.info("page" + employeePageResponse.getNumber());
		logger.info("total pages" + employeePageResponse.getTotalPages());
		responseHeader.addIntHeader("total_count", (int)employeePageResponse.getTotalElements());
		responseHeader.addIntHeader("total_pages", employeePageResponse.getTotalPages());
		responseHeader.addIntHeader("current_page", employeePageResponse.getNumber());
		responseHeader.addIntHeader("limit", employeePageResponse.getNumberOfElements());
		return ResponseEntity.status(HttpStatus.OK)
				.body(teamResponseMapper.mapListOfEmployeesToEmployeeResponse(employeePageResponse));

	}
}
