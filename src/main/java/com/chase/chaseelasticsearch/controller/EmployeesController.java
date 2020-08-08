package com.chase.chaseelasticsearch.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chase.chaseelasticsearch.documents.Employees;
import com.chase.chaseelasticsearch.exceptions.UserNotFoundException;
import com.chase.chaseelasticsearch.service.EmployeeService;
import com.chase.chaseelasticsearch.util.UtilService;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/api/v1/search")
@CrossOrigin(origins = "*")
public class EmployeesController {
	
	private static Logger logger = LoggerFactory.getLogger(EmployeesController.class);
	 
	@Autowired
	private EmployeeService employeeService;
	
	
	@GetMapping("/employee")
	public List<Employees> getMessages( @RequestHeader("authorization") String authToken,@RequestParam("q") String searchMsg, 
			@RequestParam(name = "limit", defaultValue = "10" ,required = false) int limit
			, @RequestParam(name = "page" , defaultValue = "0") int page, HttpServletResponse responseHeader) {
		long startTime = System.currentTimeMillis();
		if(authToken != null ) {
			
			Claims claim = UtilService.jwtParser(authToken);
			String userName = UtilService.getUserNameFromClaim(claim);
			List<Employees> employees = employeeService.findEmployeesByOrganizationm(userName, searchMsg, limit, page, responseHeader);
			long endTime = System.currentTimeMillis();
			logger.info("Time taken in milli seconds" + (endTime-startTime));
			
			return employees;
		}
		
		throw new UserNotFoundException("Token required");
		
	}
	
	
	/*
	 * @GetMapping("/searchEmployee") public List<Employees>
	 * getMessages(@RequestParam("q") String filter){ return
	 * employeeService.findByUsername(filter); }
	 */
}
