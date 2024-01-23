package com.rdi.geegstar.controllers;

import com.rdi.geegstar.dto.requests.BookingBillRequest;
import com.rdi.geegstar.exceptions.BookingNotFoundException;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import com.rdi.geegstar.services.BookingBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/bill")
@RequiredArgsConstructor
public class BookingBillController {

    private final BookingBillService bookingBillService;

    @PostMapping
    public ResponseEntity<?> createBookingBill(@RequestBody BookingBillRequest bookingBillRequest)
            throws UserNotFoundException, BookingNotFoundException {
        return ResponseEntity.status(CREATED).body(bookingBillService.createBookingBill(bookingBillRequest));
    }
}
