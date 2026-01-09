package com.aerodream.user_service.Dto.User;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"login", "email"})
public class UserUpdateDto {

    private String username;

    private String email;

    public boolean hasUsername() {
        return username != null;
    }

    public boolean hasEmail() {
        return email != null;
    }
}
