package com.aerodream.user_service.Dto.User;

import com.aerodream.user_service.Enum.RoleEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserResponseDto {

    private Long id;

    private String login;

    private String email;

    private Set<RoleEnum> roles;

    private LocalDateTime createdAt;

    private Set<Long> savedArtworksId;

    private Set<Long> subscriptions;

    private boolean isCreator;

    private Long creatorId;
}