package com.chase.useraccessmanagement.config;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.stereotype.Component;

import com.chase.useraccessmanagement.model.ClientCredentials;

@Component
public class KeycloakAdminClientUtils {
	
//	@Value("${keycloak.auth-server-url}")
//	private String keycloakAuthUrl;
//	@Value("${keycloak.realm}")
//	private String keycloakRealm;
//	@Value("${keycloak.resource}")
//	private String keycloakResource;
	
	public Keycloak getKeycloakClient(KeycloakSecurityContext session, ClientCredentials clientCredentials) {
//		String serverUrl = "http://10.8.0.119:8080/auth/";
//	    String realm = "chase-dev";
//	    String clientId = "user-management";
	    String clientSecret = "0959b315-e3d9-4940-9a67-9d14ea5439a0";
        return KeycloakBuilder.builder()  
                .serverUrl(clientCredentials.getKeycloakAuthUrl()) 
                .realm(clientCredentials.getKeycloakRealm()) 
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientCredentials.getKeycloakResource())
                .clientSecret(clientSecret) 
                .authorization(session.getTokenString()) 
                .build();
    }
}
