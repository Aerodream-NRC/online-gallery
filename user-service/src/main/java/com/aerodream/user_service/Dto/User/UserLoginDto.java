package com.aerodream.user_service.Dto.User;

import lombok.Data;

@Data
public class UserLoginDto {

    private String loginOrEmail;

    private String password;
}