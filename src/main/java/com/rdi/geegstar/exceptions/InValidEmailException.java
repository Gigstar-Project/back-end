package com.rdi.geegstar.exceptions;

import org.springframework.http.HttpStatus;

public class InValidEmailException extends GeegStarException {
    public InValidEmailException(String message) {
        super(message, HttpStatus.BAD_REQUEST);

    }
}
