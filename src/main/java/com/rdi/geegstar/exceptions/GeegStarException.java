package com.rdi.geegstar.exceptions;

import org.springframework.http.HttpStatus;

public class GeegStarException extends Throwable {

    private HttpStatus httpStatus;

    public GeegStarException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public GeegStarException(Throwable throwable) {
        super(throwable);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
