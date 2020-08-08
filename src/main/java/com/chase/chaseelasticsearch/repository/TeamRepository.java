package com.chase.chaseelasticsearch.repository;

import java.io.Serializable;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.chase.chaseelasticsearch.documents.Team;

@Repository
public interface TeamRepository extends ElasticsearchRepository<Team, Serializable>{

}
