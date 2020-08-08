package com.teammanagementdomainapis.exceptions;

import java.util.Date;

public class ErrorDetails {
	private Date timeStamp;
	private String error;
	private String error_message;

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	@Override
	public String toString() {
		return "ErrorDetails [timeStamp=" + timeStamp + ", error=" + error + ", error_message=" + error_message + "]";
	}

	public ErrorDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErrorDetails(Date timeStamp, String error, String error_message) {
		super();
		this.timeStamp = timeStamp;
		this.error = error;
		this.error_message = error_message;
	}

}