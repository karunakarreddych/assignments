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

import com.chase.chaseelasticsearch.documents.Team;
import com.chase.chaseelasticsearch.entities.Contacts;
import com.chase.chaseelasticsearch.repository.ContactsRepository;
import com.chase.chaseelasticsearch.repository.TeamRepository;
import com.chase.chaseelasticsearch.util.UtilService;

@Service
public class TeamService {
	
	private static Logger logger = LoggerFactory.getLogger(TeamService.class);
	
	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	private ContactsRepository contactsRepository;
	
	@Autowired
	private ElasticsearchRestTemplate elasticsearchRestTemplate;

	public List<Team> findTeams(String userName, String query, int limit, int page, HttpServletResponse responseHeader) {

		logger.info("Teams searching..");
		if (userName != null) {
			
			Contacts contact = contactsRepository.findByTypeId(userName);
			if (contact != null) {
				QueryBuilder builder = QueryBuilders.boolQuery()
						.must( QueryBuilders.boolQuery()
								.should(QueryBuilders.wildcardQuery("document.name", "*"+query+"*"))
								.should(QueryBuilders.wildcardQuery("document.members", "*"+query+"*")))
						.must(QueryBuilders.matchQuery("document.org_id", contact.getOrgId()));
				
				NativeSearchQuery queryBuilder = new NativeSearchQueryBuilder().withQuery(builder).build();
				logger.info("Query exectuing...");
				SearchHits<Team> res = elasticsearchRestTemplate.search(queryBuilder, Team.class);
				List<Team> teams = teamsMapper(res);
				logger.info("Search result" + teams);
				UtilService<Team> utilService = new UtilService<Team>();
				Page<Team> paginationResponse = utilService.convertListToPaginationList(teams, PageRequest.of(page, limit));
				 responseHeader.addIntHeader("total_count", (int) (paginationResponse.getTotalElements()));
					responseHeader.addIntHeader("total_pages", paginationResponse.getTotalPages());
					responseHeader.addIntHeader("page", paginationResponse.getNumber());
					responseHeader.addIntHeader("limit", paginationResponse.getNumberOfElements());
				return paginationResponse.getContent();
			}
		}
		
		return null;
	}

	private List<Team> teamsMapper(SearchHits<Team> res) {
		List<Team> teams = new ArrayList<Team>();
		res.forEach(searchHit -> {
			if(searchHit.getContent().isActive()) {
				teams.add(searchHit.getContent());
			}
		});
		return teams;
	}

}
