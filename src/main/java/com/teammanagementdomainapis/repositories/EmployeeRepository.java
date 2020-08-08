package com.teammanagementdomainapis.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.teammanagementdomainapis.entities.Employee;

@Repository
public interface EmployeeRepository
		extends MongoRepository<Employee, Integer>, PagingAndSortingRepository<Employee, Integer> {
	@Query("{_id: { $in: ?0 } })")
	List<Employee> findAllById(List<String> employeeIds);

	@Query("{ '_id' : ?0 }")
	Employee findByEmployeeId(String employee_id);

	@Query(value = "{'members.employee_id':?0}", count = true)
	long countRecordsByEmployeeId(String employeeId);

	@Query("{'username' :{$regex: ?0 ,$options: 'i'}}")
	Employee findByEmployeeByUsername(String username);

}
