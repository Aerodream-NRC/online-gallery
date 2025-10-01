package com.aerodream.artwork_service.Exception;

import jakarta.ws.rs.NotFoundException;

public class CollectionNotFoundException extends NotFoundException {

    public CollectionNotFoundException(final String message) {
        super(message);
    }
}
