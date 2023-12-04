package com.rdi.geegstar.controllers;

import com.rdi.geegstar.dto.requests.BookingRequest;
import com.rdi.geegstar.dto.response.BookingResponse;
import com.rdi.geegstar.exceptions.GeegStarException;
import com.rdi.geegstar.services.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/booking")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> bookTalent(@Valid @RequestBody BookingRequest bookingRequest) {
        try {
            return ResponseEntity.status(CREATED).body(bookingService.bookTalent(bookingRequest));
        } catch (GeegStarException exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }
}
