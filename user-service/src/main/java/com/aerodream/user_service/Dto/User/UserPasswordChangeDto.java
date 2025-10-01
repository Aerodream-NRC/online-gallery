package com.aerodream.user_service.Dto.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordChangeDto {

    private String currentPassword;

    private String newPassword;

    private String confirmNewPassword;
}
