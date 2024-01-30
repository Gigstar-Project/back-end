package com.rdi.geegstar.exceptions;

import org.springframework.http.HttpStatus;

public class BookingBillNotFoundException extends GeegStarException {

    public BookingBillNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

}
