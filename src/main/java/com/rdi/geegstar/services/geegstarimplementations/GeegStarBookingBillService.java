package com.rdi.geegstar.services.geegstarimplementations;

import com.rdi.geegstar.data.models.Booking;
import com.rdi.geegstar.data.models.BookingBill;
import com.rdi.geegstar.data.models.Payment;
import com.rdi.geegstar.data.models.User;
import com.rdi.geegstar.data.repositories.BookingBillRepository;
import com.rdi.geegstar.dto.requests.BookingBillRequest;
import com.rdi.geegstar.dto.requests.PayBookingBillRequest;
import com.rdi.geegstar.dto.requests.PaymentRequest;
import com.rdi.geegstar.dto.response.*;
import com.rdi.geegstar.exceptions.BookingBillNotFoundException;
import com.rdi.geegstar.exceptions.BookingNotFoundException;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import com.rdi.geegstar.services.BookingBillService;
import com.rdi.geegstar.services.BookingService;
import com.rdi.geegstar.services.PaymentService;
import com.rdi.geegstar.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class GeegStarBookingBillService implements BookingBillService {

    private final BookingBillRepository bookingBillRepository;
    private final UserService userService;
    private final BookingService bookingService;
    private final PaymentService paymentService;
    private final ModelMapper modelMapper;

    @Override
    public BookingBillResponse createBookingBill(BookingBillRequest bookingBillRequest)
            throws UserNotFoundException, BookingNotFoundException {
        User talent = userService.findUserById(bookingBillRequest.getTalentId());
        User planner = userService.findUserById(bookingBillRequest.getPlannerId());
        Booking bookingToBill = bookingService.findBookingById(bookingBillRequest.getBookingId());
        BookingBill bookingBill = new BookingBill();
        bookingBill.setBooking(bookingToBill);
        bookingBill.setTalent(talent);
        bookingBill.setPlanner(planner);
        bookingBill.setText(bookingBillRequest.getText());
        BookingBill savedBookingBill = bookingBillRepository.save(bookingBill);
        return modelMapper.map(savedBookingBill, BookingBillResponse.class);
    }

    @Override
    public BookingBill findBookingBillById(Long bookingBillId) throws BookingBillNotFoundException {
        return bookingBillRepository.findById(bookingBillId)
                .orElseThrow(
                        () -> new BookingBillNotFoundException(
                                String.format("Booking bill with Id %d is not found", bookingBillId)
                        ));
    }

    @Override
    public BookingBillPaymentResponse payBookingBill(PayBookingBillRequest payBookingBillRequest)
            throws BookingBillNotFoundException, UserNotFoundException {
        BookingBill foundBookingBill = findBookingBillById(payBookingBillRequest.getBookingBillId());
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(foundBookingBill.getAmount());
        paymentRequest.setReceiver(payBookingBillRequest.getReceiverId());
        paymentRequest.setSender(payBookingBillRequest.getSenderId());
        Payment payment = paymentService.pay(paymentRequest);
        foundBookingBill.setPayment(payment);
        foundBookingBill.setPaid(true);
        bookingBillRepository.save(foundBookingBill);
        return new BookingBillPaymentResponse("Successful");
    }

    @Override
    public BookingBillDetailsResponse getBookingBillDetails(Long bookingId) throws BookingNotFoundException {
        BookingBill bookingBill = bookingBillRepository.findByBookingId(bookingId)
                .orElseThrow(() ->
                        new BookingNotFoundException(String.format("The booking bill with id %d is not found", bookingId)));
        BookingBillDetailsResponse bookingBillDetailsResponse =
                modelMapper.map(bookingBill, BookingBillDetailsResponse.class);
        User talent = bookingBill.getTalent();
        User planner = bookingBill.getPlanner();
        GetUserResponse talentResponse = modelMapper.map(talent, GetUserResponse.class);
        GetUserResponse plannerResponse = modelMapper.map(planner, GetUserResponse.class);
        bookingBillDetailsResponse.setTalent(talentResponse);
        bookingBillDetailsResponse.setPlanner(plannerResponse);
        return bookingBillDetailsResponse;
    }

}
