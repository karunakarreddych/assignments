package com.teammanagementdomainapis.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.teammanagementdomainapis.entities.Team;

@Repository
public interface TeamRepository extends MongoRepository<Team, Integer>, PagingAndSortingRepository<Team, Integer> {

	@Query("{ 'id' : ?0 }")
	Team findTeamById(String id);

	Page<Team> findAll(Pageable pageRequest);

	@Query("{'members.employee_id':?0}")
	Page<Team> findByEmployeeId(String employeeId, Pageable pageRequest);

	@Query(value = "{'members.employee_id':?0}", count = true)
	long countTeamsByEmployeeId(String employeeId);
	
	
	 @Query(value = "{ 'id' : ?0 }", count = true) 
	 long countEmployeesByTeamId(String teamId);
	
}