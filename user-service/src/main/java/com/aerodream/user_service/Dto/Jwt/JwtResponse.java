package com.aerodream.user_service.Dto.Jwt;

import com.aerodream.user_service.Entity.UserEntity;
import com.aerodream.user_service.Enum.RoleEnum;
import lombok.Data;

import java.util.Set;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private Long id;
    private String login;
    private String email;
    private String username;
    private Set<RoleEnum> roles;
    private Boolean isCreator;

    public JwtResponse(String token, String refreshToken, UserEntity user) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.id = user.getId();
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.roles = user.getRoles();
        this.isCreator = user.isCreator();
        this.username = user.getUsername();
    }
}