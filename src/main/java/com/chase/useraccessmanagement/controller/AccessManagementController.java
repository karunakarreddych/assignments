package com.chase.useraccessmanagement.controller;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chase.useraccessmanagement.request.UserRequest;
import com.chase.useraccessmanagement.service.AccessManagementService;

@RestController
@RequestMapping("/api/v1/access-management")
public class AccessManagementController {

	@Autowired
	AccessManagementService accessManagementService;

	@PostMapping("/users")
	public ResponseEntity<Response> createUser(@RequestBody UserRequest userRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(accessManagementService.createUser(userRequest));
	}
}
