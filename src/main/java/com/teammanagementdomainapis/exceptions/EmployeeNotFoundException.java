package com.teammanagementdomainapis.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends RuntimeException {
	public EmployeeNotFoundException(String errMsg) {
		super(errMsg);
	}

	public EmployeeNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
