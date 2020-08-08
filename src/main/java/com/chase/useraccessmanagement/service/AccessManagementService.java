package com.chase.useraccessmanagement.service;

import javax.ws.rs.core.Response;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.chase.useraccessmanagement.config.KeycloakAdminClientUtils;
import com.chase.useraccessmanagement.config.KeycloakClientConfig;
import com.chase.useraccessmanagement.model.ClientCredentials;
import com.chase.useraccessmanagement.request.UserRequest;

@Service
public class AccessManagementService {

	@Autowired
	KeycloakAdminClientUtils keycloakAdminClientUtils;

	@Autowired
	KeycloakClientConfig keycloakClientConfig;

	@SuppressWarnings("unchecked")
	public Response createUser(UserRequest userRequest) {
		String userId = null;
		KeycloakPrincipal<RefreshableKeycloakSecurityContext> principal = (KeycloakPrincipal<RefreshableKeycloakSecurityContext>) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		ClientCredentials clientCredentials = keycloakClientConfig.loadKeycloakConfiguration();
		Keycloak keycloak = keycloakAdminClientUtils.getKeycloakClient(principal.getKeycloakSecurityContext(),
				clientCredentials);
		UserRepresentation userRepresentation = new UserRepresentation();
		userRepresentation.setUsername(userRequest.getUsername());
		userRepresentation.setLastName(userRequest.getLastName());
		userRepresentation.setFirstName(userRequest.getFirstName());
		userRepresentation.setEmail(userRequest.getEmail());
		userRepresentation.setEnabled(true);
		RealmResource realmResource = keycloak.realm(clientCredentials.getKeycloakRealm());
		UsersResource userRessource = realmResource.users();
		Response response = userRessource.create(userRepresentation);
		if (response.getLocation() != null) {
			userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
			CredentialRepresentation passwordCredentials = new CredentialRepresentation();
			passwordCredentials.setTemporary(false);
			passwordCredentials.setType(CredentialRepresentation.PASSWORD);
			passwordCredentials.setValue(userRequest.getPassword());
			userRessource.get(userId).resetPassword(passwordCredentials);
		}
		return response;
	}
}
