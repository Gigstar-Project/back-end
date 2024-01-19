package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.Booking;
import com.rdi.geegstar.dto.requests.*;
import com.rdi.geegstar.dto.response.AcceptBookingResponse;
import com.rdi.geegstar.dto.response.BookingResponse;
import com.rdi.geegstar.dto.response.DeclineBookingResponse;
import com.rdi.geegstar.dto.response.RegistrationResponse;
import com.rdi.geegstar.enums.Role;
import com.rdi.geegstar.exceptions.BookingNotFoundException;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import com.rdi.geegstar.exceptions.WrongDateAndTimeFormat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.rdi.geegstar.enums.EventType.BIRTHDAY_PARTY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class BookingServiceTest {

    @Autowired
    private BookingService bookTalentService;
    @Autowired
    private UserService userService;

    @Test
    public void testBookTalent() throws UserNotFoundException, WrongDateAndTimeFormat {
        BookingRequest bookTalentRequest = getBookingRequest();

        BookingResponse bookTalentResponse = bookTalentService.bookTalent(bookTalentRequest);

        assertThat(bookTalentResponse).isNotNull();
    }

    @Test
    public void testAcceptBooking() throws UserNotFoundException, BookingNotFoundException, WrongDateAndTimeFormat {
        BookingRequest bookTalentRequest = getBookingRequest();

        BookingResponse bookTalentResponse = bookTalentService.bookTalent(bookTalentRequest);

        AcceptBookingRequest acceptBookingRequest = new AcceptBookingRequest();
        acceptBookingRequest.setBookingId(bookTalentResponse.getBookingId());
        acceptBookingRequest.setTalentId(bookTalentRequest.getTalentId());

        AcceptBookingResponse acceptBookingResponse =
                bookTalentService.acceptBooking(acceptBookingRequest);

        assertThat(acceptBookingResponse).isNotNull();
    }

    @Test
    public void testDeclineBooking() throws UserNotFoundException, BookingNotFoundException, WrongDateAndTimeFormat {
        BookingRequest bookTalentRequest = getBookingRequest();

        BookingResponse bookTalentResponse = bookTalentService.bookTalent(bookTalentRequest);


        DeclineBookingResponse declineBookingResponse =
                bookTalentService.declineBooking(bookTalentResponse.getBookingId());
        assertThat(declineBookingResponse).isNotNull();
    }

    @Test
    public void testFindBookingById() throws WrongDateAndTimeFormat, UserNotFoundException, BookingNotFoundException {
        BookingRequest bookTalentRequest = getBookingRequest();
        BookingResponse bookTalentResponse = bookTalentService.bookTalent(bookTalentRequest);

        Booking foundBooking = bookTalentService.findBookingById(bookTalentResponse.getBookingId());

        assertThat(foundBooking).isNotNull();
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
        bookTalentRequest.setTalentId(talentRegistrationResponse.getId());
        bookTalentRequest.setEventDetailRequest(eventDetailsRequest);
        bookTalentRequest.setPlannerId(plannerRegistrationResponse.getId());
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
