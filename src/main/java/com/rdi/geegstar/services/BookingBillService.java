package com.rdi.geegstar.services;

import com.rdi.geegstar.dto.requests.BookingBillRequest;
import com.rdi.geegstar.dto.requests.BookingBillPaymentRequest;
import com.rdi.geegstar.dto.response.GetBookingBillDetailsResponse;
import com.rdi.geegstar.dto.response.BookingBillPaymentResponse;
import com.rdi.geegstar.dto.response.BookingBillResponse;
import com.rdi.geegstar.exceptions.BookingBillNotFoundException;
import com.rdi.geegstar.exceptions.BookingNotAcceptedException;
import com.rdi.geegstar.exceptions.BookingNotFoundException;
import com.rdi.geegstar.exceptions.UserNotFoundException;

public interface BookingBillService {
    BookingBillResponse createBookingBill(BookingBillRequest bookingBillRequest)
            throws UserNotFoundException, BookingNotFoundException, BookingNotAcceptedException;

    BookingBillPaymentResponse payBookingBill(BookingBillPaymentRequest payBookingBillRequest)
            throws BookingBillNotFoundException, UserNotFoundException;

    GetBookingBillDetailsResponse getBookingBillDetails(Long bookingId) throws BookingBillNotFoundException, BookingNotFoundException;
}
