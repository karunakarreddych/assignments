package com.chase.chaseelasticsearch.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.chase.chaseelasticsearch.documents.Messages;

@Repository
public interface MessagesRepository extends ElasticsearchRepository<Messages, Serializable>{

	List<Messages> findByBody(String filter);

	List<Messages> findByBodyAndFromjidOrTojid(String searchMsg, String userName, String targetJid);

//	List<Messages> findAllMessagesByUserNameAndTragetJidAndMessages(String userName, String targetJid, String searchMsg);

	
	
	
}
