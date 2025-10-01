package com.aerodream.user_service.Exception;

import javax.naming.AuthenticationException;

public class UserAlreadyExistException extends AuthenticationException {

    public UserAlreadyExistException(final String message) {
        super(message);
    }
}