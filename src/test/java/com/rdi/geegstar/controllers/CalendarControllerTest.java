package com.rdi.geegstar.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdi.geegstar.dto.requests.*;
import com.rdi.geegstar.dto.response.BookingResponse;
import com.rdi.geegstar.dto.response.RegistrationResponse;
import com.rdi.geegstar.enums.Role;
import com.rdi.geegstar.enums.TalentCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static com.rdi.geegstar.enums.EventType.BIRTHDAY_PARTY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class CalendarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetTalentCalendars() throws UnsupportedEncodingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
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

        final String USER_URL = "/api/v1/user";
        MvcResult talentRegistrationMvcResult = null;
        try {
            talentRegistrationMvcResult = mockMvc.perform(
                            post(String.format("%s/registration/talent", USER_URL))
                                    .content(mapper.writeValueAsString(talentRegistrationRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print())
                    .andReturn();
        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }
        assert talentRegistrationMvcResult != null;
        String talentResponseAsString = talentRegistrationMvcResult.getResponse().getContentAsString();
        RegistrationResponse talantRegistrationResponse =
                mapper.readValue(talentResponseAsString, RegistrationResponse.class);

        PlannerRegistrationRequest plannerRegistrationRequest = new PlannerRegistrationRequest();
        plannerRegistrationRequest.setFirstName("Retnaa");
        plannerRegistrationRequest.setLastName("Dayok");
        plannerRegistrationRequest.setEmail("dayokr@gmail.com");
        plannerRegistrationRequest.setPassword("password");
        plannerRegistrationRequest.setPhoneNumber("07031005737");
        plannerRegistrationRequest.setEventPlanningCompanyName("StarEvents Inc");

        MvcResult plannerRegistrationMvcResult = null;
        try {
            plannerRegistrationMvcResult = mockMvc.perform(
                            post(String.format("%s/registration/planner", USER_URL))
                                    .content(mapper.writeValueAsString(plannerRegistrationRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print())
                    .andReturn();
        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }
        assert plannerRegistrationMvcResult != null;
        String plannerResponseAsString = plannerRegistrationMvcResult.getResponse().getContentAsString();
        RegistrationResponse plannerRegistrationResponse = mapper.readValue(plannerResponseAsString, RegistrationResponse.class);


        final String BOOKING_URL = "/api/v1/booking";
        BookingRequest bookingRequest = new BookingRequest();
        EventDetailRequest eventDetailRequest = getEventDetailRequest();
        bookingRequest.setEventDetailRequest(eventDetailRequest);
        bookingRequest.setTalentId(talantRegistrationResponse.getId());
        bookingRequest.setPlannerId(plannerRegistrationResponse.getId());
        MvcResult bookingResponseMvcResult = null;
        try {
            bookingResponseMvcResult = mockMvc.perform(
                            post(BOOKING_URL)
                                    .content(mapper.writeValueAsString(bookingRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isCreated())
                    .andDo(print())
                    .andReturn();
        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }
        assert bookingResponseMvcResult != null;
        String bookingResponseAsString = bookingResponseMvcResult.getResponse().getContentAsString();
        BookingResponse bookingResponse = mapper.readValue(bookingResponseAsString, BookingResponse.class);
        AcceptBookingRequest acceptBookingRequest = new AcceptBookingRequest();
        acceptBookingRequest.setTalentId(talantRegistrationResponse.getId());
        acceptBookingRequest.setBookingId(bookingResponse.getBookingId());
        try {
            mockMvc.perform(
                            patch(String.format("%s/accept", BOOKING_URL))
                                    .content(mapper.writeValueAsString(acceptBookingRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }

        final String CALENDAR_URL = "/api/v1/calendar";
        Long talentId = talantRegistrationResponse.getId();

        try {
            mockMvc.perform(
                            get(String.format("%s/%d", CALENDAR_URL, talentId))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }
    }

    private static EventDetailRequest getEventDetailRequest() {
        EventDetailRequest eventDetailsRequest = new EventDetailRequest();
        eventDetailsRequest.setEventName("Darda's birthday party");
        eventDetailsRequest.setEventType(BIRTHDAY_PARTY);
        eventDetailsRequest.setEventDateAndTime("2024, 12, 04, 10, 30");
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
