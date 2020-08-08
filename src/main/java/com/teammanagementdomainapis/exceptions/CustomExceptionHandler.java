package com.teammanagementdomainapis.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(true));
		return new ResponseEntity<Object>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(InvalidArgumentException.class)
	public final ResponseEntity<ErrorDetails> invalidEmployeeIdEntered(InvalidArgumentException exception,
			WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.toString(), exception.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EmployeeNotFoundException.class)
	public final ResponseEntity<ErrorDetails> employeeIdNotFound(EmployeeNotFoundException exception,
			WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.NOT_FOUND.toString(),exception.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(TeamNotFoundException.class)
	public final ResponseEntity<ErrorDetails> teamIdNotFound(TeamNotFoundException exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.NOT_FOUND.toString(),exception.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public final ResponseEntity<ErrorDetails> accessDenied(AccessDeniedException exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(),HttpStatus.FORBIDDEN.toString(), exception.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
	}

}