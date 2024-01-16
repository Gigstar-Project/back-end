package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.BookingBill;
import com.rdi.geegstar.dto.requests.*;
import com.rdi.geegstar.dto.response.BookingBillPaymentResponse;
import com.rdi.geegstar.dto.response.BookingBillResponse;
import com.rdi.geegstar.dto.response.BookingResponse;
import com.rdi.geegstar.dto.response.RegistrationResponse;
import com.rdi.geegstar.enums.Role;
import com.rdi.geegstar.exceptions.BookingBillNotFoundException;
import com.rdi.geegstar.exceptions.BookingNotFoundException;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import com.rdi.geegstar.exceptions.WrongDateAndTimeFormat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static com.rdi.geegstar.enums.EventType.BIRTHDAY_PARTY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class BookingBillServiceTest {

    @Autowired
    private BookingBillService bookingBillService;
    @Autowired
    private BookingService bookTalentService;
    @Autowired
    private UserService userService;

    @Test
    @Sql("/db/insertUsers.sql")
    public void testCreateBookingBill() throws UserNotFoundException, BookingNotFoundException, WrongDateAndTimeFormat {
        BookingRequest bookTalentRequest = getBookingRequest();
        BookingResponse bookTalentResponse = bookTalentService.bookTalent(bookTalentRequest);

        BookingBillRequest bookingBillRequest = new BookingBillRequest();
        BigDecimal cost = BigDecimal.valueOf(2000000);
        bookingBillRequest.setBookingCost(cost);
        bookingBillRequest.setBookingId(bookTalentResponse.getBookingId());
        bookingBillRequest.setText("The cost covers for all expenses");
        bookingBillRequest.setPlannerId(103L);
        bookingBillRequest.setTalentId(102L);

        BookingBillResponse bookingBillResponse = bookingBillService.createBookingBill(bookingBillRequest);
        assertThat(bookingBillResponse).isNotNull();
    }

    @Test
    @Sql("/db/insertUsers.sql")
    public void testFindBookingBillById() throws WrongDateAndTimeFormat, UserNotFoundException, BookingNotFoundException, BookingBillNotFoundException {
        BookingRequest bookTalentRequest = getBookingRequest();
        BookingResponse bookTalentResponse = bookTalentService.bookTalent(bookTalentRequest);
        BookingBillRequest bookingBillRequest = new BookingBillRequest();
        BigDecimal cost = BigDecimal.valueOf(2000000);
        bookingBillRequest.setBookingCost(cost);
        bookingBillRequest.setBookingId(bookTalentResponse.getBookingId());
        bookingBillRequest.setText("The cost covers for all expenses");
        bookingBillRequest.setPlannerId(103L);
        bookingBillRequest.setTalentId(102L);
        BookingBillResponse bookingBillResponse = bookingBillService.createBookingBill(bookingBillRequest);

        BookingBill foundBookingBill = bookingBillService.findBookingBillById(bookingBillResponse.getBookingBillId());

        assertThat(foundBookingBill).isNotNull();
    }

    @Test
    @Sql("/db/insertUsers.sql")
    public void testPayBookingBill() throws WrongDateAndTimeFormat, UserNotFoundException, BookingNotFoundException, BookingBillNotFoundException {
        BookingRequest bookTalentRequest = getBookingRequest();
        BookingResponse bookTalentResponse = bookTalentService.bookTalent(bookTalentRequest);
        BookingBillRequest bookingBillRequest = new BookingBillRequest();
        BigDecimal cost = BigDecimal.valueOf(2000000);
        bookingBillRequest.setBookingCost(cost);
        bookingBillRequest.setBookingId(bookTalentResponse.getBookingId());
        bookingBillRequest.setText("The cost covers for all expenses");
        bookingBillRequest.setPlannerId(103L);
        bookingBillRequest.setTalentId(102L);
        BookingBillResponse bookingBillResponse = bookingBillService.createBookingBill(bookingBillRequest);


        BookingBillPaymentResponse bookingBillPaymentResponse =
                bookingBillService.payBookingBill(bookingBillResponse.getBookingBillId());

        assertThat(bookingBillPaymentResponse).isNotNull();
    }

    private BookingRequest getBookingRequest() throws WrongDateAndTimeFormat {
        RegistrationRequest registerRequest = new RegistrationRequest();
        registerRequest.setFirstName("Retnaa");
        registerRequest.setLastName("Dayok");
        registerRequest.setUsername("Darda");
        registerRequest.setEmail("dayokr@gmail.com");
        registerRequest.setPhoneNumber("07031005737");
        registerRequest.setPassword("password");
        registerRequest.setRole(Role.TALENT);
        RegistrationResponse talentRegistrationResponse = userService.registerUser(registerRequest);

        RegistrationRequest registerRequest2 = new RegistrationRequest();
        registerRequest.setFirstName("Retnaa");
        registerRequest.setLastName("Dayok");
        registerRequest.setUsername("Darda");
        registerRequest.setEmail("dayokr@gmail.com");
        registerRequest.setPhoneNumber("07031005737");
        registerRequest.setPassword("password");
        registerRequest.setRole(Role.PLANNER);
        RegistrationResponse plannerRegistrationResponse = userService.registerUser(registerRequest2);

        BookingRequest bookTalentRequest = new BookingRequest();
        EventDetailRequest eventDetailsRequest = getEventDetailRequest();
        bookTalentRequest.setTalent(talentRegistrationResponse.getId());
        bookTalentRequest.setEventDetailRequest(eventDetailsRequest);
        bookTalentRequest.setPlanner(plannerRegistrationResponse.getId());
        return bookTalentRequest;
    }

    private static EventDetailRequest getEventDetailRequest() throws WrongDateAndTimeFormat {
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
