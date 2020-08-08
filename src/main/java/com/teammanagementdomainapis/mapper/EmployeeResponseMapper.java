package com.teammanagementdomainapis.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.teammanagementdomainapis.entities.Employee;
import com.teammanagementdomainapis.request.EmployeeModel;

@Service
public class EmployeeResponseMapper {
	public List<EmployeeModel> mapListOfEmployeesToEmployeeResponse(Page<Employee> employeePageResponse) {
		List<Employee> employees = employeePageResponse.getContent();
		List<EmployeeModel> listOfEmployeeModel = new ArrayList<EmployeeModel>();
		employees.forEach(employee -> listOfEmployeeModel.add(new EmployeeModel(employee.getName(),
				employee.getPhotoUrl(), employee.getDesignation(), employee.getChatId())));
		return listOfEmployeeModel;
	}
}
