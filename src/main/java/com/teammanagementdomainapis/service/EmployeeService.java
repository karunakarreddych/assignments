package com.teammanagementdomainapis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.teammanagementdomainapis.entities.Employee;
import com.teammanagementdomainapis.exceptions.InvalidArgumentException;
import com.teammanagementdomainapis.repositories.EmployeeRepository;
import com.teammanagementdomainapis.repositories.TeamRepository;
import com.teammanagementdomainapis.response.EmployeeResponse;
import com.teammanagementdomainapis.util.Constants;

@Service
public class EmployeeService {
	Logger logger = LoggerFactory.getLogger(EmployeeService.class);
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	TeamRepository teamRepository;

	public Employee getEmployeeDetails(String username) {
		return employeeRepository.findByEmployeeByUsername(username);
	}

	public Page<Employee> getListOfEmployees(Integer currentPage, Integer limit) {
		EmployeeResponse employeeResponse = new EmployeeResponse();
		if (currentPage == null) {
			employeeResponse.setCurrentPage(Constants.DEFAULT_PAGE_NUMBER);
		} else if (currentPage < 0) {
			logger.error("you have entered invalid input");
			throw new InvalidArgumentException("Please Pass the valid Page no");
		} else {
			employeeResponse.setCurrentPage(currentPage);
		}

		if (limit == null) {
			employeeResponse.setLimit(Constants.DEFAULT_LIMIT);
		} else if (limit <= 0) {
			logger.error("you have entered invalid input");
			throw new InvalidArgumentException("Please Pass the valid Limit");
		} else {
			employeeResponse.setLimit(limit);
		}
		Page<Employee> listOfEmployee = null;

		listOfEmployee = employeeRepository
				.findAll(PageRequest.of(employeeResponse.getCurrentPage(), employeeResponse.getLimit()));
		return listOfEmployee;

	}
}