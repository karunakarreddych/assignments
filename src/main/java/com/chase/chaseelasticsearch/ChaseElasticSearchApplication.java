package com.chase.chaseelasticsearch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.chase.chaseelasticsearch.documents.Indexes;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableJpaRepositories
public class ChaseElasticSearchApplication {
	
	@Value("${document.index.employees}")
	private String employeeIndex;
	
	@Value("${document.index.messages}")
	private String messagesIndex;
	
	@Value("${document.index.teams}")
	private String teamsIndex;

	public static void main(String[] args) {
		SpringApplication.run(ChaseElasticSearchApplication.class, args);
	}

	@Bean
	Indexes index() {
		return new Indexes(employeeIndex, messagesIndex, teamsIndex);
	}
	
}


