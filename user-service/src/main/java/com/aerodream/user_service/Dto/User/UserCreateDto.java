package com.aerodream.user_service.Dto.User;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserCreateDto {

    private String login;

    @Email
    private String email;

    private String password;

    private String confirmPassword;
}