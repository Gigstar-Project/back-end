package com.rdi.geegstar.services;

import com.rdi.geegstar.dto.requests.*;
import com.rdi.geegstar.dto.response.BookingResponse;
import com.rdi.geegstar.dto.response.GetTalentCalendarsResponse;
import com.rdi.geegstar.dto.response.RegistrationResponse;
import com.rdi.geegstar.enums.TalentCategory;
import com.rdi.geegstar.exceptions.BookingNotFoundException;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.rdi.geegstar.enums.EventType.BIRTHDAY_PARTY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
public class CalenderServiceTest {

    @Autowired
    private CalendarService calenderService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookingService bookingService;

    @Test
    public void testGetTalentCalenders() throws UserNotFoundException, BookingNotFoundException {
        // Given
        TalentRegistrationRequest talentRegistrationRequest = getTalentRegistrationRequest();
        PortfolioRequest portfolioRequest = new PortfolioRequest();
        portfolioRequest.setFirstLink("https://www.youtube.com/watch?v=1qw5ITr3k9E&t=780s");
        talentRegistrationRequest.setPortfolioRequest(portfolioRequest);
        RegistrationResponse talentRegistrationResponse = userService.registerUser(talentRegistrationRequest);

        PlannerRegistrationRequest plannerRegistrationRequest = getPlannerRegistrationRequest();
        RegistrationResponse plannerRegistrationResponse = userService.registerUser(plannerRegistrationRequest);

        BookingRequest bookTalentRequest = getBookingRequest(talentRegistrationResponse, plannerRegistrationResponse);
        BookingResponse bookTalentResponse = bookingService.bookTalent(bookTalentRequest);

        AcceptBookingRequest acceptBookingRequest = getAcceptBookingRequest(bookTalentResponse, bookTalentRequest);
        bookingService.acceptBooking(acceptBookingRequest);

        // When
        Long talentId = talentRegistrationResponse.getId();
        List<GetTalentCalendarsResponse> getTalentCalenderResponse = calenderService.getTalentCalendars(talentId);
        log.info("Calender :: {}", getTalentCalenderResponse);

        // Assert
        assertThat(getTalentCalenderResponse).isNotNull();
    }

    private static AcceptBookingRequest getAcceptBookingRequest(BookingResponse bookTalentResponse, BookingRequest bookTalentRequest) {
        AcceptBookingRequest acceptBookingRequest = new AcceptBookingRequest();
        acceptBookingRequest.setBookingId(bookTalentResponse.getBookingId());
        acceptBookingRequest.setTalentId(bookTalentRequest.getTalentId());
        return acceptBookingRequest;
    }

    private BookingRequest getBookingRequest(RegistrationResponse talentRegistrationResponse, RegistrationResponse plannerRegistrationResponse) {
        BookingRequest bookTalentRequest = new BookingRequest();
        EventDetailRequest eventDetailsRequest = getEventDetailRequest();
        bookTalentRequest.setTalentId(talentRegistrationResponse.getId());
        bookTalentRequest.setEventDetailRequest(eventDetailsRequest);
        bookTalentRequest.setPlannerId(plannerRegistrationResponse.getId());
        return bookTalentRequest;
    }

    private static PlannerRegistrationRequest getPlannerRegistrationRequest() {
        PlannerRegistrationRequest plannerRegistrationRequest = new PlannerRegistrationRequest();
        plannerRegistrationRequest.setFirstName("Retnaa");
        plannerRegistrationRequest.setLastName("Dayok");
        plannerRegistrationRequest.setEmail("dayokr@gmail.com");
        plannerRegistrationRequest.setPassword("password");
        plannerRegistrationRequest.setPhoneNumber("07031005737");
        plannerRegistrationRequest.setEventPlanningCompanyName("StarEvents Inc");
        return plannerRegistrationRequest;
    }

    private static TalentRegistrationRequest getTalentRegistrationRequest() {
        TalentRegistrationRequest talentRegistrationRequest = new TalentRegistrationRequest();
        talentRegistrationRequest.setFirstName("Retnaa");
        talentRegistrationRequest.setLastName("Dayok");
        talentRegistrationRequest.setEmail("dayokr@gmail.com");
        talentRegistrationRequest.setPassword("password");
        talentRegistrationRequest.setPhoneNumber("07031005737");
        talentRegistrationRequest.setTalentCategory(TalentCategory.ARTISTE);
        talentRegistrationRequest.setBio("A young vibrant talented afro musician, singer of the hit song Banger."
                + " An award winning star");
        talentRegistrationRequest.setDisplayName("Jay Benjis");
        return talentRegistrationRequest;
    }


    private EventDetailRequest getEventDetailRequest() {
        EventDetailRequest eventDetailsRequest = new EventDetailRequest();
        eventDetailsRequest.setEventName("Darda's birthday party");
        eventDetailsRequest.setEventType(BIRTHDAY_PARTY);
        eventDetailsRequest.setEventDateAndTime("2024, 12, 04, 10, 30");
        AddressRequest addressRequest = getAddressRequest();
        eventDetailsRequest.setEventAddress(addressRequest);
        return eventDetailsRequest;
    }

    private AddressRequest getAddressRequest() {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setCity("Lagos mainland");
        addressRequest.setCountry("Nigeria");
        addressRequest.setState("Lagos");
        addressRequest.setBuildingNumber(4634L);
        addressRequest.setStreet("Yaba");
        return addressRequest;
    }
}
