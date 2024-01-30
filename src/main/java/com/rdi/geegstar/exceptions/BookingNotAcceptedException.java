package com.rdi.geegstar.exceptions;

import org.springframework.http.HttpStatus;

public class BookingNotAcceptedException extends GeegStarException {
    public BookingNotAcceptedException(String message) {
        super(message, HttpStatus.PRECONDITION_FAILED);
    }
}
