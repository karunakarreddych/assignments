package com.chase.chaseelasticsearch.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.joda.time.Duration;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chase.chaseelasticsearch.documents.Messages;
import com.chase.chaseelasticsearch.dto.GlobalSearchDTO;
import com.chase.chaseelasticsearch.exceptions.UserNotFoundException;
import com.chase.chaseelasticsearch.service.MessageService;
import com.chase.chaseelasticsearch.util.UtilService;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/search")
@CrossOrigin(origins = "*")
public class MessageController {

	private static Logger logger = LoggerFactory.getLogger(MessageController.class);

	@Autowired
	private MessageService messageService;
	
	@GetMapping("/global")
	@ApiOperation("Global Search API")
	public  List<GlobalSearchDTO> getMessagesByBody(@RequestHeader("authorization") String authToken,
			@RequestParam String queryParam , @RequestParam(name = "limit", defaultValue = "10" ,required = false) int limit
			, @RequestParam(name = "page" , defaultValue = "0") int page, 
			HttpServletResponse responseHeader) throws JSONException{
		long startTime = System.currentTimeMillis();
		if(authToken != null ) {
			Claims claim = UtilService.jwtParser(authToken);
			String userName = UtilService.getUserNameFromClaim(claim);
			List<GlobalSearchDTO> res =  messageService.findByAllIndexs(queryParam, limit, page, responseHeader, userName); 
			long endTime = System.currentTimeMillis();
			Duration duration = new Duration(startTime, endTime); 
			
			logger.info("Time taken in  milliseconds" + duration.getMillis());
			 return res;
		}
		throw new UserNotFoundException("Token required");
	}
	
	@GetMapping("/message/{targetJid}")
	public List<Messages> getMessages(@PathVariable("targetJid") String targetJid, @RequestHeader("authorization") String authToken,
			@RequestParam("q") String searchMsg, @RequestParam(name = "limit", defaultValue = "10" ,required = false) int limit
			, @RequestParam(name = "page" , defaultValue = "0") int page, HttpServletResponse responseHeader) {
		long startTime = System.currentTimeMillis();
		if(authToken != null ) {
			Claims claim = UtilService.jwtParser(authToken);
			String userName = UtilService.getUserNameFromClaim(claim);
			List<Messages> messages = messageService.findMessages(userName, targetJid, searchMsg, limit, page, responseHeader);
			Duration duration = new Duration(startTime, System.currentTimeMillis()); 
			logger.info("Time taken in seconds" + duration.getMillis());
			return messages;
		}
		
		throw new UserNotFoundException("Token required");
		
	}
	
	
}
