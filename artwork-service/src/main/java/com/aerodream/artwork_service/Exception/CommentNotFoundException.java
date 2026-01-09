package com.aerodream.artwork_service.Exception;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException(final String message) {
        super(message);
    }
}