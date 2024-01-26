package com.rdi.geegstar.controllers;

import com.rdi.geegstar.dto.requests.BookingBillPaymentRequest;
import com.rdi.geegstar.dto.requests.BookingBillRequest;

import com.rdi.geegstar.exceptions.BookingBillNotFoundException;
import com.rdi.geegstar.exceptions.BookingNotFoundException;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import com.rdi.geegstar.services.BookingBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

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

    @GetMapping("{bookingId}")
    public ResponseEntity<?> getBookingBillDetails(@PathVariable Long bookingId) {
        try {
            return ResponseEntity.status(OK).body(bookingBillService.getBookingBillDetails(bookingId));
        } catch (BookingBillNotFoundException | BookingNotFoundException exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }

    @PostMapping("/payment")
    public ResponseEntity<?> payBookingBill(@RequestBody BookingBillPaymentRequest bookingBillPaymentRequest) {
        try {
            return ResponseEntity.status(OK).body(bookingBillService.payBookingBill(bookingBillPaymentRequest));
        } catch (UserNotFoundException | BookingBillNotFoundException exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }
}
