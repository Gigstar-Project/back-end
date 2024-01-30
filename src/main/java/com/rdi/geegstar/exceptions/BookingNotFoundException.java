package com.rdi.geegstar.exceptions;

import org.springframework.http.HttpStatus;

public class BookingNotFoundException extends GeegStarException {
    public BookingNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
