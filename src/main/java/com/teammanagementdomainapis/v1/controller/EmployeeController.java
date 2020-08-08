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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teammanagementdomainapis.entities.Employee;
import com.teammanagementdomainapis.exceptions.EmployeeNotFoundException;
import com.teammanagementdomainapis.exceptions.InvalidArgumentException;
import com.teammanagementdomainapis.mapper.EmployeeResponseMapper;
import com.teammanagementdomainapis.repositories.EmployeeRepository;
import com.teammanagementdomainapis.request.EmployeeModel;
import com.teammanagementdomainapis.request.EmployeeRequest;
import com.teammanagementdomainapis.response.EmployeeUpdatePhotoResponse;
import com.teammanagementdomainapis.service.EmployeeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/teams-management")
public class EmployeeController {
	private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	EmployeeService employeeService;

	@Autowired
	EmployeeResponseMapper employeeResponseMapper;

	@PostMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("create employee")
	public String createEmployee(@RequestBody Employee employee) {
		employeeRepository.save(employee);
		return "Employee Created successfully...";
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/employees/{employeeId}/photo", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation("update employee photo by employeeId")
	public ResponseEntity<EmployeeUpdatePhotoResponse> updateEmployeePhoto(
			@PathVariable(name = "employeeId") String employeeId, @RequestBody EmployeeRequest request) {
		EmployeeUpdatePhotoResponse emp = new EmployeeUpdatePhotoResponse();
		Employee employee = employeeRepository.findByEmployeeId(employeeId);
		if (employee != null) {
			employee.setPhotoUrl(request.getPhoto_url());
			employee.setUpdatedAt(Instant.now());
			employee = employeeRepository.save(employee);
			emp.setPhoto_url(employee.getPhotoUrl());
			emp.setMessage("Photo Updated Successfully!!!");

		} else {

			logger.error("employee data not found for this employeeId");
			throw new InvalidArgumentException("Please pass the valid EmployeeId");
		}

		return ResponseEntity.status(HttpStatus.OK).body(emp);

	}

	@GetMapping(value = "/employees", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation("list of employees")
	public ResponseEntity<List<EmployeeModel>> getAllEmployees(HttpServletResponse responseHeader,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "limit", required = false) Integer limit) {
		Page<Employee> employeePageResponse = employeeService.getListOfEmployees(page, limit);
		logger.info("total_count" + employeePageResponse.getTotalElements());
		logger.info("total_pages" + employeePageResponse.getTotalPages());
		logger.info("current_page" + employeePageResponse.getNumber());
		logger.info("limit" + employeePageResponse.getNumberOfElements());
		responseHeader.addIntHeader("total_count", (int) employeePageResponse.getTotalElements());
		responseHeader.addIntHeader("total_pages", employeePageResponse.getTotalPages());
		responseHeader.addIntHeader("current_page", employeePageResponse.getNumber());
		responseHeader.addIntHeader("limit", employeePageResponse.getNumberOfElements());

		return ResponseEntity.status(HttpStatus.OK)
				.body(employeeResponseMapper.mapListOfEmployeesToEmployeeResponse(employeePageResponse));
	}

	@GetMapping(value = "/employees/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("employee details")
	public ResponseEntity<Employee> getEmployeeDetails(@PathVariable(name = "username") String username) {
		Employee employeeDetails = employeeService.getEmployeeDetails(username);
		if (employeeDetails != null) {
			return ResponseEntity.status(HttpStatus.OK).body(employeeDetails);
		} else {

			logger.error("employee data not found with this username ");
			throw new EmployeeNotFoundException("Employee data Not Found with this username   " + username);
		}

	}
}
