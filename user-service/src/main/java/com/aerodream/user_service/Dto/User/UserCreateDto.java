package com.aerodream.user_service.Dto.User;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonPropertyOrder({"login", "email", "password", "confirmPassword"})
@Getter
@Setter
@NoArgsConstructor
public class UserCreateDto {

    private String login;

    @Email
    private String email;

    private String password;

    private String confirmPassword;

}