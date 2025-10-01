package com.aerodream.user_service.Exception;

import jakarta.ws.rs.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(final String message) {
        super(message);
    }
}