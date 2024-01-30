package com.rdi.geegstar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgumentFieldExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errorsMapped = new HashMap<>();
        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errorsMapped.put(error.getField(), error.getDefaultMessage()));
        return errorsMapped;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String, String> handleUserNotFoundException(UserNotFoundException exception) {
        return getMappedError(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailIsTakenException.class)
    public Map<String, String> handleEmailTakenException(EmailIsTakenException exception) {
        return getMappedError(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(EmailConfirmationFailedException.class)
    public Map<String, String> handleEmailConfirmationFailedException(EmailConfirmationFailedException exception) {
        return getMappedError(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookingBillNotFoundException.class)
    public Map<String, String> handleBookingNotFoundException(BookingBillNotFoundException exception) {
        return getMappedError(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BookingNotAcceptedException.class)
    public Map<String, String> handleBookingNotAcceptedException(BookingNotAcceptedException exception) {
        return getMappedError(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookingBillNotFoundException.class)
    public Map<String, String> handleBookingBillNotFoundException(BookingBillNotFoundException exception) {
        return getMappedError(exception.getMessage());
    }

    private static Map<String, String> getMappedError(String exception) {
        Map<String, String> error = new HashMap<>();
        error.put("Error ", exception);
        return error;
    }


}
