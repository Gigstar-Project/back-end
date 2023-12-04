package com.rdi.geegstar.services;

import com.rdi.geegstar.dto.requests.AcceptBookingRequest;
import com.rdi.geegstar.dto.requests.BookingRequest;
import com.rdi.geegstar.dto.response.AcceptBookingResponse;
import com.rdi.geegstar.dto.response.BookingResponse;
import com.rdi.geegstar.dto.response.DeclineBookingResponse;
import com.rdi.geegstar.exceptions.BookingNotFoundException;
import com.rdi.geegstar.exceptions.UserNotFoundException;

public interface BookingService {

    BookingResponse bookTalent(BookingRequest bookCreativeTalentRequest) throws UserNotFoundException;

    AcceptBookingResponse acceptBooking(AcceptBookingRequest acceptBookingRequest) throws BookingNotFoundException;

    DeclineBookingResponse declineBooking(Long bookingId) throws BookingNotFoundException;
}