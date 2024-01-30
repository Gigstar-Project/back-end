package com.rdi.geegstar.exceptions;

import org.springframework.http.HttpStatus;

public class EmailConfirmationFailedException extends GeegStarException{
    public EmailConfirmationFailedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
