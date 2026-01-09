package com.aerodream.artwork_service.Exception;

public class CollectionNotFoundException extends RuntimeException {

    public CollectionNotFoundException(final String message) {
        super(message);
    }
}
