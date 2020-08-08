package com.teammanagementdomainapis.response;

import java.util.List;

import com.teammanagementdomainapis.request.EmployeeModel;

public class EmployeeResponse extends Response {
	List<EmployeeModel> employees;

	Integer currentPage;
	Integer limit;

	public List<EmployeeModel> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeModel> employees) {
		this.employees = employees;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public EmployeeResponse(List<EmployeeModel> employees, Integer currentPage, Integer limit) {
		super();
		this.employees = employees;
		this.currentPage = currentPage;
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "EmployeeResponse [employees=" + employees + ", currentPage=" + currentPage + ", limit=" + limit + "]";
	}

	public EmployeeResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
}
