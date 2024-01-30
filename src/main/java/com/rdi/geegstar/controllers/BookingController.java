package com.rdi.geegstar.controllers;

import com.rdi.geegstar.dto.requests.AcceptBookingRequest;
import com.rdi.geegstar.dto.requests.BookingRequest;
import com.rdi.geegstar.dto.requests.GetUserBookingsRequest;
import com.rdi.geegstar.exceptions.BookingNotFoundException;
import com.rdi.geegstar.exceptions.GeegStarException;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import com.rdi.geegstar.services.BookingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/booking")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> bookTalent( @RequestBody @Valid BookingRequest bookingRequest)
            throws UserNotFoundException {
        return ResponseEntity.status(CREATED).body(bookingService.bookTalent(bookingRequest));
    }

    @PatchMapping("/accept")
    public ResponseEntity<?> acceptBooking(@RequestBody AcceptBookingRequest acceptBookingRequest)
            throws UserNotFoundException, BookingNotFoundException {
        return ResponseEntity.ok().body(bookingService.acceptBooking(acceptBookingRequest));
    }

    @PatchMapping("/decline/{bookingId}")
    public ResponseEntity<?> declineBooking(@PathVariable Long bookingId) throws BookingNotFoundException {
        return ResponseEntity.ok().body(bookingService.declineBooking(bookingId));
    }

    @GetMapping
    public ResponseEntity<?> getUserBookings(@RequestBody GetUserBookingsRequest getUserBookingsRequest)
            throws UserNotFoundException {
        return ResponseEntity.ok().body(bookingService.getUserBookings(getUserBookingsRequest));
    }
}
