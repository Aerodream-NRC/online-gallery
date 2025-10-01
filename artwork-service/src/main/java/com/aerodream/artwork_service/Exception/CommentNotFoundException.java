package com.aerodream.artwork_service.Exception;

import jakarta.ws.rs.NotFoundException;

public class CommentNotFoundException extends NotFoundException {

    public CommentNotFoundException(final String message) {
        super(message);
    }
}