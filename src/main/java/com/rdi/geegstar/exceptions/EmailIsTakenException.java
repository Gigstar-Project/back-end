package com.rdi.geegstar.exceptions;

import org.springframework.http.HttpStatus;

public class EmailIsTakenException extends GeegStarException {
    public EmailIsTakenException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
