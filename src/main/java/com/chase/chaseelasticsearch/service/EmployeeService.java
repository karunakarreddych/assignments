package com.chase.chaseelasticsearch.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import com.chase.chaseelasticsearch.documents.Employees;
import com.chase.chaseelasticsearch.entities.Contacts;
import com.chase.chaseelasticsearch.repository.ContactsRepository;
import com.chase.chaseelasticsearch.repository.EmployeeRepository;
import com.chase.chaseelasticsearch.util.UtilService;

@Service
public class EmployeeService {

	private static Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ContactsRepository contactsRepository;

	@Autowired
	private ElasticsearchRestTemplate elasticsearchRestTemplate;

	public List<Employees> findEmployeesByOrganizationm(String userName, String searchMsg, int limit, int page,
			HttpServletResponse responseHeader) {
		logger.info("Employee searching..");
		if (userName != null) {

			Contacts contact = contactsRepository.findByTypeId(userName);
			logger.info("Contact org id" + contact.getOrgId());
			if (contact != null) {
				QueryBuilder builder = QueryBuilders.boolQuery()
						.must( QueryBuilders.boolQuery()
								.should(QueryBuilders.wildcardQuery("document.username", "*"+searchMsg+"*"))
								.should(QueryBuilders.wildcardQuery("document.name", "*"+searchMsg+"*")))
//								.should(QueryBuilders.wildcardQuery("document.email", "*"+searchMsg+"*")))
						.must(QueryBuilders.matchQuery("document.org_id", contact.getOrgId()));

				NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(builder).build();
				logger.info("Query exectuing...");
				SearchHits<Employees> res = elasticsearchRestTemplate.search(query, Employees.class);
				List<Employees> employees = employeesMapper(res);
				logger.info("Search result" + employees);
				UtilService<Employees> utilService = new UtilService<Employees>();
				Page<Employees> pagedResponse = utilService.convertListToPaginationList(employees,
						PageRequest.of(page, limit));
				responseHeader.addIntHeader("total_count", (int) (pagedResponse.getTotalElements()));
				responseHeader.addIntHeader("total_pages", pagedResponse.getTotalPages());
				responseHeader.addIntHeader("page", pagedResponse.getNumber());
				responseHeader.addIntHeader("limit", pagedResponse.getNumberOfElements());
				return pagedResponse.getContent();
			}

//			return employeeRepository.findByUsernameAndOrganizationId(searchMsg, contact.getOrgId());
		}
		return null;
	}

	private List<Employees> employeesMapper(SearchHits<Employees> res) {
		List<Employees> employees = new ArrayList<Employees>();
		res.forEach(searchHit -> {
			if (searchHit.getContent().getActive().equals("true")) {
				employees.add(searchHit.getContent());
			}
		});
		return employees;
	}


	
}
