package com.aerodream.artwork_service.Exception;

import jakarta.ws.rs.NotFoundException;

public class ArtworkNotFoundException extends NotFoundException {

    public ArtworkNotFoundException(final String message) {
        super(message);
    }
}
