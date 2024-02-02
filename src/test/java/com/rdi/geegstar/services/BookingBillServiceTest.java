package com.rdi.geegstar.services;

import com.rdi.geegstar.dto.requests.*;
import com.rdi.geegstar.dto.response.*;
import com.rdi.geegstar.enums.Role;
import com.rdi.geegstar.enums.TalentCategory;
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
        bookingService.acceptBooking(acceptBookingRequest);

        BookingBillRequest bookingBillRequest = new BookingBillRequest();
        BigDecimal bookingCost = BigDecimal.valueOf(2000000);
        bookingBillRequest.setBookingCost(bookingCost);
        bookingBillRequest.setBookingId(bookTalentResponse.getBookingId());
        bookingBillRequest.setText("The cost covers for all expenses");
        bookingBillRequest.setPlannerId(talentRegistrationResponse.getId());
        bookingBillRequest.setTalentId(plannerRegistrationResponse.getId());

        BookingBillResponse bookingBillResponse = bookingBillService.createBookingBill(bookingBillRequest);
        assertThat(bookingBillResponse).isNotNull();
    }

    @Test
    public void testGetBookingBillDetails()
            throws UserNotFoundException, BookingNotFoundException, BookingBillNotFoundException, BookingNotAcceptedException {
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
        bookingService.acceptBooking(acceptBookingRequest);

        BookingBillRequest bookingBillRequest = new BookingBillRequest();
        BigDecimal bookingCost = BigDecimal.valueOf(2000000);
        bookingBillRequest.setBookingCost(bookingCost);
        bookingBillRequest.setBookingId(bookTalentResponse.getBookingId());
        bookingBillRequest.setText("The cost covers for all expenses");
        bookingBillRequest.setPlannerId(talentRegistrationResponse.getId());
        bookingBillRequest.setTalentId(plannerRegistrationResponse.getId());
        bookingBillService.createBookingBill(bookingBillRequest);

        GetBookingBillDetailsResponse getBookingBillDetailsResponse =
                bookingBillService.getBookingBillDetails(bookTalentResponse.getBookingId());

        assertThat(getBookingBillDetailsResponse).isNotNull();
    }

    @Test
    public void testPayBookingBill()
            throws UserNotFoundException,
            BookingNotFoundException, BookingBillNotFoundException, BookingNotAcceptedException {
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
        bookingService.acceptBooking(acceptBookingRequest);

        BookingBillRequest bookingBillRequest = new BookingBillRequest();
        BigDecimal bookingCost = BigDecimal.valueOf(2000000);
        bookingBillRequest.setBookingCost(bookingCost);
        bookingBillRequest.setBookingId(bookTalentResponse.getBookingId());
        bookingBillRequest.setText("The cost covers for all expenses");
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
