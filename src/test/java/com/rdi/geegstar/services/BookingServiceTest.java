package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.Booking;
import com.rdi.geegstar.dto.requests.*;
import com.rdi.geegstar.dto.response.*;
import com.rdi.geegstar.enums.Role;
import com.rdi.geegstar.enums.TalentCategory;
import com.rdi.geegstar.exceptions.BookingNotFoundException;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.rdi.geegstar.enums.EventType.BIRTHDAY_PARTY;
import static com.rdi.geegstar.enums.Role.PLANNER;
import static com.rdi.geegstar.enums.Role.TALENT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserService userService;

    @Test
    public void testBookTalent() throws UserNotFoundException{
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
        PortfolioRequest portfolioRequest = new PortfolioRequest();
        portfolioRequest.setFirstLink("https://www.youtube.com/watch?v=1qw5ITr3k9E&t=780s");
        talentRegistrationRequest.setPortfolioRequest(portfolioRequest);
        RegistrationResponse talentRegistrationResponse = userService.registerUser(talentRegistrationRequest);

        PlannerRegistrationRequest plannerRegistrationRequest = new PlannerRegistrationRequest();
        plannerRegistrationRequest.setFirstName("Retnaa");
        plannerRegistrationRequest.setLastName("Dayok");
        plannerRegistrationRequest.setEmail("dayokr@gmail.com");
        plannerRegistrationRequest.setPassword("password");
        plannerRegistrationRequest.setPhoneNumber("07031005737");
        plannerRegistrationRequest.setEventPlanningCompanyName("StarEvents Inc");
        RegistrationResponse plannerRegistrationResponse = userService.registerUser(plannerRegistrationRequest);

        BookingRequest bookTalentRequest = new BookingRequest();
        EventDetailRequest eventDetailsRequest = getEventDetailRequest();
        bookTalentRequest.setTalentId(talentRegistrationResponse.getId());
        bookTalentRequest.setEventDetailRequest(eventDetailsRequest);
        bookTalentRequest.setPlannerId(plannerRegistrationResponse.getId());

        BookingResponse bookTalentResponse = bookingService.bookTalent(bookTalentRequest);

        assertThat(bookTalentResponse).isNotNull();
    }

    @Test
    public void testAcceptBooking() throws UserNotFoundException, BookingNotFoundException{
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
        PortfolioRequest portfolioRequest = new PortfolioRequest();
        portfolioRequest.setFirstLink("https://www.youtube.com/watch?v=1qw5ITr3k9E&t=780s");
        talentRegistrationRequest.setPortfolioRequest(portfolioRequest);
        RegistrationResponse talentRegistrationResponse = userService.registerUser(talentRegistrationRequest);

        PlannerRegistrationRequest plannerRegistrationRequest = new PlannerRegistrationRequest();
        plannerRegistrationRequest.setFirstName("Retnaa");
        plannerRegistrationRequest.setLastName("Dayok");
        plannerRegistrationRequest.setEmail("dayokr@gmail.com");
        plannerRegistrationRequest.setPassword("password");
        plannerRegistrationRequest.setPhoneNumber("07031005737");
        plannerRegistrationRequest.setEventPlanningCompanyName("StarEvents Inc");
        RegistrationResponse plannerRegistrationResponse = userService.registerUser(plannerRegistrationRequest);

        BookingRequest bookTalentRequest = new BookingRequest();
        EventDetailRequest eventDetailsRequest = getEventDetailRequest();
        bookTalentRequest.setTalentId(talentRegistrationResponse.getId());
        bookTalentRequest.setEventDetailRequest(eventDetailsRequest);
        bookTalentRequest.setPlannerId(plannerRegistrationResponse.getId());

        BookingResponse bookTalentResponse = bookingService.bookTalent(bookTalentRequest);

        AcceptBookingRequest acceptBookingRequest = new AcceptBookingRequest();
        acceptBookingRequest.setBookingId(bookTalentResponse.getBookingId());
        acceptBookingRequest.setTalentId(bookTalentRequest.getTalentId());

        AcceptBookingResponse acceptBookingResponse =
                bookingService.acceptBooking(acceptBookingRequest);

        assertThat(acceptBookingResponse).isNotNull();
    }

    @Test
    public void testDeclineBooking() throws UserNotFoundException, BookingNotFoundException{
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
        PortfolioRequest portfolioRequest = new PortfolioRequest();
        portfolioRequest.setFirstLink("https://www.youtube.com/watch?v=1qw5ITr3k9E&t=780s");
        talentRegistrationRequest.setPortfolioRequest(portfolioRequest);
        RegistrationResponse talentRegistrationResponse = userService.registerUser(talentRegistrationRequest);

        PlannerRegistrationRequest plannerRegistrationRequest = new PlannerRegistrationRequest();
        plannerRegistrationRequest.setFirstName("Retnaa");
        plannerRegistrationRequest.setLastName("Dayok");
        plannerRegistrationRequest.setEmail("dayokr@gmail.com");
        plannerRegistrationRequest.setPassword("password");
        plannerRegistrationRequest.setPhoneNumber("07031005737");
        plannerRegistrationRequest.setEventPlanningCompanyName("StarEvents Inc");
        RegistrationResponse plannerRegistrationResponse = userService.registerUser(plannerRegistrationRequest);

        BookingRequest bookTalentRequest = new BookingRequest();
        EventDetailRequest eventDetailsRequest = getEventDetailRequest();
        bookTalentRequest.setTalentId(talentRegistrationResponse.getId());
        bookTalentRequest.setEventDetailRequest(eventDetailsRequest);
        bookTalentRequest.setPlannerId(plannerRegistrationResponse.getId());

        BookingResponse bookTalentResponse = bookingService.bookTalent(bookTalentRequest);

        DeclineBookingResponse declineBookingResponse =
                bookingService.declineBooking(bookTalentResponse.getBookingId());
        assertThat(declineBookingResponse).isNotNull();
    }

    @Test
    public void testFindBookingById() throws UserNotFoundException, BookingNotFoundException {
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
        PortfolioRequest portfolioRequest = new PortfolioRequest();
        portfolioRequest.setFirstLink("https://www.youtube.com/watch?v=1qw5ITr3k9E&t=780s");
        talentRegistrationRequest.setPortfolioRequest(portfolioRequest);
        RegistrationResponse talentRegistrationResponse = userService.registerUser(talentRegistrationRequest);

        PlannerRegistrationRequest plannerRegistrationRequest = new PlannerRegistrationRequest();
        plannerRegistrationRequest.setFirstName("Retnaa");
        plannerRegistrationRequest.setLastName("Dayok");
        plannerRegistrationRequest.setEmail("dayokr@gmail.com");
        plannerRegistrationRequest.setPassword("password");
        plannerRegistrationRequest.setPhoneNumber("07031005737");
        plannerRegistrationRequest.setEventPlanningCompanyName("StarEvents Inc");
        RegistrationResponse plannerRegistrationResponse = userService.registerUser(plannerRegistrationRequest);

        BookingRequest bookTalentRequest = new BookingRequest();
        EventDetailRequest eventDetailsRequest = getEventDetailRequest();
        bookTalentRequest.setTalentId(talentRegistrationResponse.getId());
        bookTalentRequest.setEventDetailRequest(eventDetailsRequest);
        bookTalentRequest.setPlannerId(plannerRegistrationResponse.getId());

        BookingResponse bookTalentResponse = bookingService.bookTalent(bookTalentRequest);

        Booking foundBooking = bookingService.findBookingById(bookTalentResponse.getBookingId());

        assertThat(foundBooking).isNotNull();
    }

    @Test
    public void testGetUserBookingsForTalent() throws UserNotFoundException {
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
        PortfolioRequest portfolioRequest = new PortfolioRequest();
        portfolioRequest.setFirstLink("https://www.youtube.com/watch?v=1qw5ITr3k9E&t=780s");
        talentRegistrationRequest.setPortfolioRequest(portfolioRequest);
        RegistrationResponse talentRegistrationResponse = userService.registerUser(talentRegistrationRequest);
        RegistrationResponse talentRegistrationResponse2 = userService.registerUser(talentRegistrationRequest);

        PlannerRegistrationRequest plannerRegistrationRequest = new PlannerRegistrationRequest();
        plannerRegistrationRequest.setFirstName("Retnaa");
        plannerRegistrationRequest.setLastName("Dayok");
        plannerRegistrationRequest.setEmail("dayokr@gmail.com");
        plannerRegistrationRequest.setPassword("password");
        plannerRegistrationRequest.setPhoneNumber("07031005737");
        plannerRegistrationRequest.setEventPlanningCompanyName("StarEvents Inc");
        RegistrationResponse plannerRegistrationResponse = userService.registerUser(plannerRegistrationRequest);


        EventDetailRequest eventDetailsRequest = getEventDetailRequest();
        BookingRequest bookTalentRequest = new BookingRequest();
        bookTalentRequest.setTalentId(talentRegistrationResponse.getId());
        bookTalentRequest.setEventDetailRequest(eventDetailsRequest);
        bookTalentRequest.setPlannerId(plannerRegistrationResponse.getId());

        BookingRequest bookTalentRequest2 = new BookingRequest();
        bookTalentRequest2.setTalentId(talentRegistrationResponse2.getId());
        bookTalentRequest2.setEventDetailRequest(eventDetailsRequest);
        bookTalentRequest2.setPlannerId(plannerRegistrationResponse.getId());

        for (int index = 0; index <= 2; index++) {
            bookingService.bookTalent(bookTalentRequest);
            bookingService.bookTalent(bookTalentRequest2);
        }

        GetUserBookingsRequest getUserBookingsRequest = new GetUserBookingsRequest();
        int pageNumber = 1;
        int numberOfBookingsToPage = 2;
        getUserBookingsRequest.setUserId(talentRegistrationResponse2.getId());
        getUserBookingsRequest.setPageNumber(pageNumber);
        getUserBookingsRequest.setPageSize(numberOfBookingsToPage);
        getUserBookingsRequest.setUserRole(TALENT);

        List<UserBookingResponse> userBookingsResponse = bookingService.getUserBookings(getUserBookingsRequest);
        System.out.println(userBookingsResponse);
        Assertions.assertThat(userBookingsResponse).hasSize(numberOfBookingsToPage);
    }

    @Test
    public void testGetUserBookingsForPlanner() throws UserNotFoundException {
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
        PortfolioRequest portfolioRequest = new PortfolioRequest();
        portfolioRequest.setFirstLink("https://www.youtube.com/watch?v=1qw5ITr3k9E&t=780s");
        talentRegistrationRequest.setPortfolioRequest(portfolioRequest);
        RegistrationResponse talentRegistrationResponse = userService.registerUser(talentRegistrationRequest);

        TalentRegistrationRequest talentRegistrationRequest2 = new TalentRegistrationRequest();
        talentRegistrationRequest2.setFirstName("Emmanuel");
        talentRegistrationRequest2.setLastName("Omale");
        talentRegistrationRequest2.setEmail("othnielomale@gmail.com");
        talentRegistrationRequest2.setPassword("password");
        talentRegistrationRequest2.setPhoneNumber("08101112303");
        talentRegistrationRequest2.setTalentCategory(TalentCategory.ARTISTE);
        talentRegistrationRequest2.setBio("A singer of the hit song Oyi. An award winning star");
        talentRegistrationRequest2.setDisplayName("Oyimehnn");
        PortfolioRequest portfolioRequest2 = new PortfolioRequest();
        portfolioRequest2.setFirstLink("https://www.youtube.com/watch?v=1qw5ITr3k9E&t=780s");
        talentRegistrationRequest2.setPortfolioRequest(portfolioRequest2);
        RegistrationResponse talentRegistrationResponse2 = userService.registerUser(talentRegistrationRequest2);

        PlannerRegistrationRequest plannerRegistrationRequest = new PlannerRegistrationRequest();
        plannerRegistrationRequest.setFirstName("Retnaa");
        plannerRegistrationRequest.setLastName("Dayok");
        plannerRegistrationRequest.setEmail("dayokr@gmail.com");
        plannerRegistrationRequest.setPassword("password");
        plannerRegistrationRequest.setPhoneNumber("07031005737");
        plannerRegistrationRequest.setEventPlanningCompanyName("StarEvents Inc");
        RegistrationResponse plannerRegistrationResponse = userService.registerUser(plannerRegistrationRequest);

        EventDetailRequest eventDetailsRequest = getEventDetailRequest();
        BookingRequest bookTalentRequest = new BookingRequest();
        bookTalentRequest.setTalentId(talentRegistrationResponse.getId());
        bookTalentRequest.setEventDetailRequest(eventDetailsRequest);
        bookTalentRequest.setPlannerId(plannerRegistrationResponse.getId());

        BookingRequest bookTalentRequest2 = new BookingRequest();
        bookTalentRequest2.setTalentId(talentRegistrationResponse2.getId());
        bookTalentRequest2.setEventDetailRequest(eventDetailsRequest);
        bookTalentRequest2.setPlannerId(plannerRegistrationResponse.getId());

        for (int index = 0; index <= 2; index++) {
            bookingService.bookTalent(bookTalentRequest);
            bookingService.bookTalent(bookTalentRequest2);
        }

        GetUserBookingsRequest getUserBookingsRequest = new GetUserBookingsRequest();
        int pageNumber = 1;
        int numberOfBookingsToPage = 2;
        getUserBookingsRequest.setUserId(plannerRegistrationResponse.getId());
        getUserBookingsRequest.setPageNumber(pageNumber);
        getUserBookingsRequest.setPageSize(numberOfBookingsToPage);
        getUserBookingsRequest.setUserRole(PLANNER);

        List<UserBookingResponse> userBookingsResponse = bookingService.getUserBookings(getUserBookingsRequest);
        System.out.println(userBookingsResponse);
        Assertions.assertThat(userBookingsResponse).isNotNull();
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
