package com.aerodream.user_service.Exception;

import jakarta.ws.rs.NotFoundException;

public class CreatorNotFoundException extends NotFoundException {

    public CreatorNotFoundException(final String message) {
        super(message);
    }
}