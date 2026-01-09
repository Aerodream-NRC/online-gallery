package com.aerodream.user_service.Service;

import com.aerodream.user_service.Dto.User.UserCreateDto;
import com.aerodream.user_service.Exception.UserAlreadyExistException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakClient {
    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Value("${keycloak.admin.client-id}")
    private String adminClientId;

    private Keycloak keycloak;

    @PostConstruct
    public void init() {
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakUrl)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(adminClientId)
                .clientSecret(clientSecret)
                .build();
    }

    public void createUser(UserCreateDto createDto, Long userId) {
        log.info("Creating user in keycloak : {}", createDto.getLogin());

        try {
            List<UserRepresentation> existingUsers = keycloak.realm(realm)
                    .users()
                    .search(createDto.getLogin());

            if (!existingUsers.isEmpty()) {
                throw new UserAlreadyExistException("User with login " + createDto.getLogin() + " already exists in keycloak");
            }

            UserRepresentation user = getRepresentation(createDto, userId);

            keycloak.realm(realm).users().create(user);

        } catch (Exception e) {
            log.error("Error creating user in keycloak", e);

            throw new RuntimeException("Failed to create user in Keycloak", e);
        }
    }

    private static @NotNull UserRepresentation getRepresentation(UserCreateDto createDto, Long userId) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(createDto.getLogin());
        user.setEmail(createDto.getEmail());
        user.setEnabled(true);
        user.setEmailVerified(false);

        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(createDto.getPassword());
        credentials.setTemporary(false);
        user.setCredentials(Collections.singletonList(credentials));

        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("UserId", Collections.singletonList(userId.toString()));
        user.setAttributes(attributes);
        return user;
    }
}
