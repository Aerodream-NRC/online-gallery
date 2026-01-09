package com.aerodream.user_service.Exception;

public class CreatorNotFoundException extends RuntimeException {

    public CreatorNotFoundException(final String message) {
        super(message);
    }
}