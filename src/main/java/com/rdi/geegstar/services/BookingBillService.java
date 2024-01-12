package com.rdi.geegstar.services;

import com.rdi.geegstar.dto.requests.BookingBillRequest;
import com.rdi.geegstar.dto.response.BookingBillResponse;
import com.rdi.geegstar.exceptions.UserNotFoundException;

public interface BookingBillService {
    BookingBillResponse createBookingBill(BookingBillRequest bookingBillRequest) throws UserNotFoundException;
}
