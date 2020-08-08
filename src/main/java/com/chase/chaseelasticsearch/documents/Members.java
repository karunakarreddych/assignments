package com.chase.chaseelasticsearch.documents;

import org.springframework.data.elasticsearch.annotations.Field;

public class Members {
	
	@Field("employee_id")
	private String employeeId;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
	

}
