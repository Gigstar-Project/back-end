package com.rdi.geegstar.services;

import com.rdi.geegstar.dto.requests.*;
import com.rdi.geegstar.dto.response.*;
import com.rdi.geegstar.enums.Role;
import com.rdi.geegstar.exceptions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static com.rdi.geegstar.enums.EventType.BIRTHDAY_PARTY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class BookingBillServiceTest {

    @Autowired
    private BookingBillService bookingBillService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserService userService;

    @Test
    public void testCreateBookingBill()
            throws UserNotFoundException, BookingNotFoundException, BookingNotAcceptedException {
        BookingRequest bookingRequest = getBookingRequest();
        BookingResponse bookingResponse = bookingService.bookTalent(bookingRequest);
        AcceptBookingRequest acceptBookingRequest = new AcceptBookingRequest();
        acceptBookingRequest.setBookingId(bookingResponse.getBookingId());
        acceptBookingRequest.setTalentId(bookingRequest.getTalentId());
        bookingService.acceptBooking(acceptBookingRequest);
        BookingBillRequest bookingBillRequest = new BookingBillRequest();
        BigDecimal bookingCost = BigDecimal.valueOf(2000000);
        bookingBillRequest.setBookingCost(bookingCost);
        bookingBillRequest.setBookingId(bookingResponse.getBookingId());
        bookingBillRequest.setText("The cost covers for all expenses");
        RegistrationResponse talentRegistrationResponse = getUserRegistrationResponse(Role.TALENT);
        RegistrationResponse plannerRegistrationResponse = getUserRegistrationResponse(Role.PLANNER);
        bookingBillRequest.setPlannerId(talentRegistrationResponse.getId());
        bookingBillRequest.setTalentId(plannerRegistrationResponse.getId());

        BookingBillResponse bookingBillResponse = bookingBillService.createBookingBill(bookingBillRequest);
        assertThat(bookingBillResponse).isNotNull();
    }

    @Test
    public void testGetBookingBillDetails()
            throws UserNotFoundException, BookingNotFoundException, BookingBillNotFoundException, BookingNotAcceptedException {
        RegistrationResponse talentRegistrationResponse = getUserRegistrationResponse(Role.TALENT);
        RegistrationResponse plannerRegistrationResponse = getUserRegistrationResponse(Role.PLANNER);
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setTalentId(talentRegistrationResponse.getId());
        bookingRequest.setPlannerId(plannerRegistrationResponse.getId());
        EventDetailRequest eventDetailRequest = getEventDetailRequest();
        bookingRequest.setEventDetailRequest(eventDetailRequest);
        BookingResponse bookingResponse = bookingService.bookTalent(bookingRequest);
        AcceptBookingRequest acceptBookingRequest = new AcceptBookingRequest();
        acceptBookingRequest.setBookingId(bookingResponse.getBookingId());
        acceptBookingRequest.setTalentId(bookingRequest.getTalentId());
        bookingService.acceptBooking(acceptBookingRequest);
        BookingBillRequest bookingBillRequest = new BookingBillRequest();
        BigDecimal bookingCost = BigDecimal.valueOf(2000000);
        bookingBillRequest.setBookingCost(bookingCost);
        bookingBillRequest.setBookingId(bookingResponse.getBookingId());
        bookingBillRequest.setText("The cost covers for all expenses");
        bookingBillRequest.setPlannerId(talentRegistrationResponse.getId());
        bookingBillRequest.setTalentId(plannerRegistrationResponse.getId());
        bookingBillService.createBookingBill(bookingBillRequest);

        GetBookingBillDetailsResponse getBookingBillDetailsResponse =
                bookingBillService.getBookingBillDetails(bookingResponse.getBookingId());

        assertThat(getBookingBillDetailsResponse).isNotNull();
    }

    @Test
    public void testPayBookingBill()
            throws UserNotFoundException,
            BookingNotFoundException, BookingBillNotFoundException, BookingNotAcceptedException {
        BookingRequest bookingRequest = getBookingRequest();
        BookingResponse bookingResponse = bookingService.bookTalent(bookingRequest);
        AcceptBookingRequest acceptBookingRequest = new AcceptBookingRequest();
        acceptBookingRequest.setBookingId(bookingResponse.getBookingId());
        acceptBookingRequest.setTalentId(bookingRequest.getTalentId());
        bookingService.acceptBooking(acceptBookingRequest);
        BookingBillRequest bookingBillRequest = new BookingBillRequest();
        BigDecimal cost = BigDecimal.valueOf(2000000);
        bookingBillRequest.setBookingCost(cost);
        bookingBillRequest.setBookingId(bookingResponse.getBookingId());
        bookingBillRequest.setText("The cost covers for all expenses");
        RegistrationResponse talentRegistrationResponse = getUserRegistrationResponse(Role.TALENT);
        RegistrationResponse plannerRegistrationResponse = getUserRegistrationResponse(Role.PLANNER);
        bookingBillRequest.setPlannerId(talentRegistrationResponse.getId());
        bookingBillRequest.setTalentId(plannerRegistrationResponse.getId());
        BookingBillResponse bookingBillResponse = bookingBillService.createBookingBill(bookingBillRequest);

        BookingBillPaymentRequest bookingBillPaymentRequest = new BookingBillPaymentRequest();
        bookingBillPaymentRequest.setBookingBillId(bookingBillResponse.getBookingBillId());
        bookingBillPaymentRequest.setSenderId(plannerRegistrationResponse.getId());
        bookingBillPaymentRequest.setReceiverId(talentRegistrationResponse.getId());

        BookingBillPaymentResponse bookingBillPaymentResponse =
                bookingBillService.payBookingBill(bookingBillPaymentRequest);

        assertThat(bookingBillPaymentResponse).isNotNull();
    }

    private BookingRequest getBookingRequest() {
        RegistrationResponse talentRegistrationResponse = getUserRegistrationResponse(Role.TALENT);

        RegistrationResponse plannerRegistrationResponse = getUserRegistrationResponse(Role.PLANNER);

        BookingRequest bookTalentRequest = new BookingRequest();
        EventDetailRequest eventDetailsRequest = getEventDetailRequest();
        bookTalentRequest.setTalentId(talentRegistrationResponse.getId());
        bookTalentRequest.setEventDetailRequest(eventDetailsRequest);
        bookTalentRequest.setPlannerId(plannerRegistrationResponse.getId());
        return bookTalentRequest;
    }

    private RegistrationResponse getUserRegistrationResponse(Role role) {
        RegistrationRequest talentRegisterRequest = new RegistrationRequest();
        talentRegisterRequest.setFirstName("Retnaa");
        talentRegisterRequest.setLastName("Dayok");
        talentRegisterRequest.setEmail("dayokr@gmail.com");
        talentRegisterRequest.setPhoneNumber("07031005737");
        talentRegisterRequest.setPassword("password");
        talentRegisterRequest.setRole(role);
        return userService.registerUser(talentRegisterRequest);
    }

    private static EventDetailRequest getEventDetailRequest() {
        EventDetailRequest eventDetailsRequest = new EventDetailRequest();
        eventDetailsRequest.setEventName("Darda's birthday party");
        eventDetailsRequest.setEventType(BIRTHDAY_PARTY);
        eventDetailsRequest.setEventDateAndTime("2023, 12, 04, 10, 30");
        AddressRequest addressRequest = getAddressRequest();
        eventDetailsRequest.setEventAddress(addressRequest);
        return eventDetailsRequest;
    }

    private static AddressRequest getAddressRequest() {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setCity("Lagos mainland");
        addressRequest.setCountry("Nigeria");
        addressRequest.setState("Lagos");
        addressRequest.setBuildingNumber(4634L);
        addressRequest.setStreet("Yaba");
        return addressRequest;
    }
}
