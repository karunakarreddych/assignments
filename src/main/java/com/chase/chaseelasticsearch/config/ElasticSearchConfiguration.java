package com.chase.chaseelasticsearch.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@Configuration
public class ElasticSearchConfiguration {

	
	@Value("${elasticsearch.host}")
    private String esHost;

    @Value("${elasticsearch.port}")
    private int esPort;

    @Value("${elasticsearch.clustername}")
    private String esClusterName;
    
    @Bean
    public RestHighLevelClient client() {
//        ClientConfiguration clientConfiguration 
//            = ClientConfiguration.builder()
//                .connectedTo("10.8.0.119:9200").withConnectTimeout(5000000)
//                .build();
        
        
        RestClientBuilder builder = RestClient.builder(
        	    new HttpHost(esHost, esPort))
        	    .setRequestConfigCallback(
        	        new RestClientBuilder.RequestConfigCallback() {
        	            @Override
        	            public RequestConfig.Builder customizeRequestConfig(
        	                    RequestConfig.Builder requestConfigBuilder) {
        	                return requestConfigBuilder
        	                    .setConnectTimeout(5000000)
        	                    .setSocketTimeout(6000000);
        	            }
        	        });
        
        RestHighLevelClient client = new RestHighLevelClient(builder);
        
//        return RestClients.create(clientConfiguration).rest();
        return client;
    }
 
    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(client());
    }


	
}
