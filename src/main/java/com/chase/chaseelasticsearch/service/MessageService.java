package com.chase.chaseelasticsearch.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.joda.time.Duration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.chase.chaseelasticsearch.documents.Employees;
import com.chase.chaseelasticsearch.documents.Members;
import com.chase.chaseelasticsearch.documents.Messages;
import com.chase.chaseelasticsearch.documents.Team;
import com.chase.chaseelasticsearch.dto.GlobalSearchDTO;
import com.chase.chaseelasticsearch.entities.Contacts;
import com.chase.chaseelasticsearch.exceptions.UserNotFoundException;
import com.chase.chaseelasticsearch.repository.ContactsRepository;
import com.chase.chaseelasticsearch.repository.MessagesRepository;
import com.chase.chaseelasticsearch.util.UtilService;

@Service
public class MessageService {

	@Value("${elasticsearch.global.url}")
	private String elasticSearchGlobalUrl;
	
	@Autowired
	private MessagesRepository messagesRepository;
	
	@Value("${openfire.host}")
	private String openfireHost;
	
	@Value("${openfire.room.serviceName}")
	private String roomServiceName;
	
	private static Logger  logger = LoggerFactory.getLogger(MessageService.class);
	
	@Autowired
	private ElasticsearchRestTemplate elasticsearchRestTemplate;
	
	@Autowired
	private ContactsRepository contactsRepository;
	
	

	public List<Messages> findByBody(String filter) {
		return messagesRepository.findByBody(filter);
	}

	public List<Messages> findMessages(String userName, String targetJid, String searchMsg, int limit, int page, HttpServletResponse responseHeader) {
		if(targetJid != null) {
//			messagesRepository.findAllMessagesByUserNameAndTragetJidAndMessages(userName, targetJid, searchMsg);
//			List<Messages> messages = messagesRepository.findByBodyAndFromjidOrTojid(searchMsg, userName, targetJid);
			
//			
//			QueryBuilder builder = QueryBuilders.boolQuery()
//					.must(QueryBuilders.multiMatchQuery(searchMsg).field("body").fuzziness(Fuzziness.AUTO))
//					.must(QueryBuilders.multiMatchQuery(targetJid).field("fromjid").field("tojid"))
//					.must(QueryBuilders.multiMatchQuery(userName).field("fromjid").field("tojid"));
			 
			
			QueryBuilder builder = QueryBuilders.boolQuery()
					.must(QueryBuilders.wildcardQuery("body", "*"+searchMsg+"*"))
					.must(QueryBuilders.multiMatchQuery(targetJid).field("fromjid").field("tojid"))
					.must(QueryBuilders.multiMatchQuery(userName).field("fromjid").field("tojid"));
			 
			NativeSearchQuery queryBuilder = new NativeSearchQueryBuilder().withQuery(builder)
					.withPageable(PageRequest.of(page, limit)).build();
			logger.info("Query exectuing...");
			SearchHits<Messages> resp = elasticsearchRestTemplate.search(queryBuilder, Messages.class);
			List<Messages> messages = messagesMapper(resp, userName);
//			
//			UtilService<Messages> utilService = new UtilService<Messages>();
//			 Page<Messages> res =  utilService.convertListToPaginationList(messages, PageRequest.of(page, limit));
//			    responseHeader.addIntHeader("total_count", (int) (res.getTotalElements()));
//				responseHeader.addIntHeader("total_pages", res.getTotalPages());
//				responseHeader.addIntHeader("page", res.getNumber());
//				responseHeader.addIntHeader("limit", res.getNumberOfElements());
			 return messages;
		}
		throw new UserNotFoundException("Target Jid should not be null value"); 	 	
	}

	public  List<GlobalSearchDTO> findByAllIndexs(String queryParam, int size, int page, HttpServletResponse responseHeader, String userName) throws JSONException {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String query = generateGlobalSearchQuery(userName, queryParam, size, page);
		HttpEntity<String> request = new HttpEntity<String>(query, headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(elasticSearchGlobalUrl);
		logger.info("Golabal Search API calling ");
		long startTime = System.currentTimeMillis();
		ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, request, String.class);
		Duration duration = new Duration(startTime, System.currentTimeMillis()); 
		logger.info("Response time "+ duration.getMillis());
		if(response.getStatusCode().is2xxSuccessful()) {
			String body = response.getBody();
			logger.info("Body reurned "+ body);
			JSONObject jsonObj = new JSONObject(body);
			logger.info("in json"+ jsonObj);
			logger.info("Parsing start at time :"+ new Date());
			List<GlobalSearchDTO> res = parseJsonObjectByPagination(jsonObj, size, page, responseHeader, userName);
			logger.info("Parsing start done at: "+ new Date());
			return res;
		}
		
		return null;
	}
	
	
	
	

	public  List<GlobalSearchDTO> parseJsonObjectByPagination(JSONObject json, int limit, int page, HttpServletResponse responseHeader, String userName) throws JSONException {
	    if (json == null) {
	        return null;
	    }
	    List<GlobalSearchDTO> searchRes = new ArrayList<>();
	    json =  (JSONObject) json.get("hits");
	    JSONArray array = (JSONArray) json.get("hits");
	    for (int i = 0 ; i < array.length(); i++) {
	    	GlobalSearchDTO dto = new GlobalSearchDTO();
	    	JSONObject result = 	(JSONObject) array.get(i);
	    	String index = (String) result.get("_index");
	    	JSONObject source = (JSONObject) result.get("_source");
	    	dto.setIndex(index);
	    	if(index.equals("employees")) {
	    		JSONObject document = (JSONObject) source.get("document");
	    		Employees employee = new Employees();
	    		String orgId = (String) document.get("org_id");	
	    		String name = (String) document.get("name");
	    		String Id = (String) document.get("_id");
	    		if(document.has("email")) {
	    			String email = (String) document.get("email");
	    			employee.setEmail(email);
	    		}
	    		if(document.has("username")) {
	    			String username = (String) document.get("username");
	    			employee.setUsername(username);
	    		}
	    		if(document.has("about")) {
	    			if(!JSONObject.NULL.equals(document.get("about")) && document.get("about") != null) {
		    			String about = (String) document.get("about");
		    			employee.setAbout(about);
	    			}
	    		}
	    		
	    		if(document.has("photo_url")) {
	    			if(!JSONObject.NULL.equals(document.get("photo_url")) && document.get("photo_url") != null) {
	    				String photoUrl = (String) document.get("photo_url");
		    			employee.setPhotoUrl(photoUrl);
	    			}
	    			
	    		}
	    		employee.setOrganizationId(orgId);
	    		employee.setName(name);
	    		employee.setId(Id);
	    		employee.setActive("true");
	    		dto.setEmployees(employee);
	    	}else if(index.equals("messages")) {
	    		Integer messageid = (Integer) source.get("messageid");
	    		String body = (String) source.get("body");
	    		String tojid = (String) source.get("tojid");
	    		String fromjid = (String) source.get("fromjid");
	    		Long sentdate = (Long) source.get("sentdate");
	    		Messages messages = new Messages();
	    		messages.setBody(body);
	    		messages.setMessageid(String.valueOf(messageid));
	    		messages.setTojid(tojid);
	    		messages.setFromjid(fromjid);
	    		messages.setSentdate( String.valueOf(sentdate));
	    		String targetJid = "";
	    		String user = userName+"@"+openfireHost;
				if(user.equals(fromjid)) {
					targetJid = tojid;
				}else if(user.equals(tojid)) {
					targetJid = fromjid;
				}
	    		messages.setTargetJid(targetJid);
	    		if(source.has("stanza")) {
	    			messages.setStanza((String) source.get("stanza"));
	    		}
	    		
	    		Contacts contact = contactsRepository.findByTypeIdAndActive(targetJid.replace("@", "").replace(openfireHost, "").replace(roomServiceName, "").trim(), true);
				if(contact != null) {
					messages.setName(contact.getName());
				}
	    		dto.setMessages(messages);
	    	}else if(index.equals("teams")) {
	    		Team team = new Team();
	    		JSONObject document = (JSONObject) source.get("document");
	    		String orgId = (String) document.get("org_id");
	    		String Id = (String) document.get("_id");
	    		String name = (String) document.get("name");
	    		if(document.has("members")) {
	    			String memberString = (String) document.get("members").toString();
	    			String[] members  =memberString.split(",");
	    			List<Members> mem = new ArrayList<>();
	    			for (String string : members) {
						Members mem2 = new Members();
						mem2.setEmployeeId(string);
						mem.add(mem2);
					}
	    			team.setMembers(mem);
	    		}
	    		team.setOrganizationId(orgId);
	    		team.setId(Id);
	    		team.setName(name);
	    		team.setActive(true);
	    		dto.setTeams(team);
	    	}
	    	searchRes.add(dto);
		}
	    logger.info("hits are "+ array.length());
//	    Page<GlobalSearchDTO> res =  utilService.convertListToPaginationList(searchRes, PageRequest.of(page, limit));
	    responseHeader.addIntHeader("total_count", searchRes.size());
//		responseHeader.addIntHeader("total_pages", res.getTotalPages());
		responseHeader.addIntHeader("page", page);
		responseHeader.addIntHeader("limit", searchRes.size());
	    return searchRes;
	}
	
	
	private List<Messages> messagesMapper(SearchHits<Messages> res, String userName) {
		List<Messages> messages = new ArrayList<Messages>();
		res.forEach(searchHit -> {
			String user = userName+"@"+openfireHost;
			if(user.equals(searchHit.getContent().getFromjid())) {
				searchHit.getContent().setTargetJid(searchHit.getContent().getTojid());
			}else if(user.equals(searchHit.getContent().getTojid())) {
				searchHit.getContent().setTargetJid(searchHit.getContent().getFromjid());
			}
			
			Contacts contact = contactsRepository.findByTypeIdAndActive(searchHit.getContent().getTargetJid().replace("@", "").replace(openfireHost, "").replace(roomServiceName, "").trim(), true);
			if(contact != null) {
				searchHit.getContent().setName(contact.getName());
			}

			messages.add(searchHit.getContent());
		});
		return messages;
	}
	
	
	private String generateGlobalSearchQuery(String userName, String queryParam, int size, int page) {
		Contacts contact = contactsRepository.findByTypeIdAndActive(userName, true);
		int from = 0;
		if(page != 0) {
			from = page*size;
		}
		//		int 
		String query = "{\r\n" + 
				"   \"size\": "+size+",\r\n" +
				"   \"from\": "+from+",\r\n" +
				"   \"query\":{\r\n" + 
				"      \"bool\":{\r\n" + 
				"         \"must\":[\r\n" + 
				"            {\r\n" + 
				"               \"bool\":{\r\n" + 
				"                  \"should\":[{\r\n" + 
				"                     \"bool\":{\r\n" + 
				"                        \"must\":[\r\n" + 
				"                           {\r\n" + 
				"                              \"term\":{\r\n" + 
				"                                 \"_index\":\"messages\"\r\n" + 
				"                              }\r\n" + 
				"                           },\r\n" + 
				"                           {\r\n" + 
				"                              \"query_string\":{\r\n" + 
				"                                 \"query\":\"*"+userName+"*\",\r\n" + 
				"                                 \"fields\":[\r\n" + 
				"                                    \"fromjid\"\r\n" + 
				"                                 ]\r\n" + 
				"                              }\r\n" + 
				"                           },\r\n" + 
				"                           {\r\n" + 
				"                              \"query_string\":{\r\n" + 
				"                                 \"query\":\"*"+queryParam+"*\",\r\n" + 
				"                                 \"fields\":[\r\n" + 
//				"                                    \"stanza\",\r\n" + 
				"                                    \"body\"\r\n" + 
				"                                 ]\r\n" + 
				"                              }\r\n" + 
				"                           }\r\n" + 
				"                        ]\r\n" + 
				"                     }\r\n" + 
				"                  },\r\n" + 
				"				  {\r\n" + 
				"                     \"bool\":{\r\n" + 
				"                        \"must\":[\r\n" + 
				"                           {\r\n" + 
				"                              \"term\":{\r\n" + 
				"                                 \"_index\":\"employees\"\r\n" + 
				"                              }\r\n" + 
				"                           },\r\n" + 
				"                           {\r\n" + 
				"                              \"query_string\":{\r\n" + 
				"                                 \"query\":\""+contact.getOrgId()+"\",\r\n" + 
				"                                  \"fields\":[\r\n" + 
				"                                    \"document.org_id\"\r\n" + 
				"                                 ]\r\n" + 
				"                              }\r\n" + 
				"                           },\r\n" + 
				"                           {\r\n" + 
				"                              \"query_string\":{\r\n" + 
				"                                 \"query\":\"*"+queryParam+"*\",\r\n" + 
				"                                 \"fields\":[\r\n" + 
				"                                   \"document.username\",\r\n" + 
				"                                    \"document.name\"\r\n" + 
//				"                                    \"document.email\"\r\n" + 
				"                                 ]\r\n" + 
				"                              }\r\n" + 
				"                           }\r\n" + 
				"                        ]\r\n" + 
				"                     }\r\n" + 
				"                  },\r\n" + 
				"				  {\r\n" + 
				"                     \"bool\":{\r\n" + 
				"                        \"must\":[\r\n" + 
				"                           {\r\n" + 
				"                              \"term\":{\r\n" + 
				"                                 \"_index\":\"teams\"\r\n" + 
				"                              }\r\n" + 
				"                           },\r\n" + 
				"                           {\r\n" + 
				"                              \"query_string\":{\r\n" + 
				"                                 \"query\":\""+contact.getOrgId()+"\",\r\n" + 
				"                                  \"fields\":[\r\n" + 
				"                                    \"document.org_id\"\r\n" + 
				"                                 ]\r\n" + 
				"                              }\r\n" + 
				"                           },\r\n" +
				"                           {\r\n" + 
				"                              \"query_string\":{\r\n" + 
				"                                 \"query\":\"*"+queryParam+"*\",\r\n" + 
				"                                 \"fields\":[\r\n" + 
				"                                   \"document.name\",\r\n" + 
				"                                    \"document.members\"\r\n" + 
				"                                 ]\r\n" + 
				"                              }\r\n" + 
				"                           }\r\n" + 
				"                        ]\r\n" + 
				"                     }\r\n" + 
				"                  }\r\n" + 
				"				  ]\r\n" + 
				"               }\r\n" + 
				"            }\r\n" + 
				"         ]\r\n" + 
				"      }\r\n" + 
				"   }\r\n" + 
				"}";
//		logger.info("Query : " + query);
		return query;
	}
	
	
	
	
}
