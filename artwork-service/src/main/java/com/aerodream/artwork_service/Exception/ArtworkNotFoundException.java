package com.aerodream.artwork_service.Exception;

public class ArtworkNotFoundException extends RuntimeException {

    public ArtworkNotFoundException(final String message) {
        super(message);
    }
}
