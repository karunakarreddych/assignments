package com.chase.chaseelasticsearch.repository;

import java.io.Serializable;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.chase.chaseelasticsearch.documents.Employees;

@Repository
public interface EmployeeRepository  extends ElasticsearchRepository<Employees, Serializable>{

	
//	@Query("{\"bool\" : {\"must\" : [ {\"field\" : {\"document.organization_id\" : \"?1\"}}]} \n" + 
//			"}")
//	List<Employees> findByUsernameAndOrganizationId(String searchMsg, String orgId);

//	List<EmployeesDocument> findByUsernameAndOrganizationId(String searchMsg, String orgId);
//
//	List<EmployeesDocument> findByUsername(String filter);

}
