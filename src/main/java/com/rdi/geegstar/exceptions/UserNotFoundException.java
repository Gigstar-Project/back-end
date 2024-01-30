package com.rdi.geegstar.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends GeegStarException {
    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
