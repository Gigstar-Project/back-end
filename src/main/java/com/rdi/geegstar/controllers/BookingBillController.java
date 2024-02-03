package com.rdi.geegstar.controllers;

import com.rdi.geegstar.dto.requests.BookingBillPaymentRequest;
import com.rdi.geegstar.dto.requests.BookingBillRequest;

import com.rdi.geegstar.dto.response.BookingBillPaymentResponse;
import com.rdi.geegstar.dto.response.BookingBillResponse;
import com.rdi.geegstar.dto.response.GetBookingBillDetailsResponse;
import com.rdi.geegstar.exceptions.BookingBillNotFoundException;
import com.rdi.geegstar.exceptions.BookingNotAcceptedException;
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
    public ResponseEntity<BookingBillResponse> createBookingBill(@RequestBody BookingBillRequest bookingBillRequest)
            throws UserNotFoundException, BookingNotAcceptedException, BookingNotFoundException {
        return ResponseEntity.status(CREATED).body(bookingBillService.createBookingBill(bookingBillRequest));
    }

    @GetMapping("{bookingId}")
    public ResponseEntity<GetBookingBillDetailsResponse> getBookingBillDetails(@PathVariable Long bookingId)
            throws BookingNotFoundException, BookingBillNotFoundException {
        return ResponseEntity.status(OK).body(bookingBillService.getBookingBillDetails(bookingId));
    }

    @PostMapping("/payment")
    public ResponseEntity<BookingBillPaymentResponse> payBookingBill(@RequestBody BookingBillPaymentRequest bookingBillPaymentRequest)
            throws UserNotFoundException, BookingBillNotFoundException {
        return ResponseEntity.status(OK).body(bookingBillService.payBookingBill(bookingBillPaymentRequest));
    }
}
