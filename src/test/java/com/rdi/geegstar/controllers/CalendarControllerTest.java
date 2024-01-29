package com.rdi.geegstar.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdi.geegstar.dto.requests.*;
import com.rdi.geegstar.dto.response.BookingResponse;
import com.rdi.geegstar.dto.response.RegistrationResponse;
import com.rdi.geegstar.enums.Role;
import com.rdi.geegstar.exceptions.WrongDateAndTimeFormat;
import com.rdi.geegstar.services.UserService;
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
public class CalendarControllerTest {

    @Autowired
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetTalentCalendars() throws UnsupportedEncodingException, JsonProcessingException, WrongDateAndTimeFormat {
        RegistrationRequest talentRegisterRequest = new RegistrationRequest();
        talentRegisterRequest.setFirstName("Retnaa");
        talentRegisterRequest.setLastName("Dayok");
        talentRegisterRequest.setDisplayName("Darda");
        talentRegisterRequest.setEmail("dayokr@gmail.com");
        talentRegisterRequest.setPhoneNumber("07031005737");
        talentRegisterRequest.setPassword("password");
        talentRegisterRequest.setRole(Role.TALENT);

        RegistrationRequest plannerRegisterRequest = new RegistrationRequest();
        plannerRegisterRequest.setFirstName("Retnaa");
        plannerRegisterRequest.setLastName("Dayok");
        plannerRegisterRequest.setDisplayName("Darda");
        plannerRegisterRequest.setEmail("dayokr@gmail.com");
        plannerRegisterRequest.setPhoneNumber("07031005737");
        plannerRegisterRequest.setPassword("password");
        plannerRegisterRequest.setRole(Role.PLANNER);

        ObjectMapper mapper = new ObjectMapper();
        final String USER_URL = "/api/v1/user";
        MvcResult talentRegistrationMvcResult = null;
        try {
            talentRegistrationMvcResult = mockMvc.perform(
                            post(USER_URL)
                                    .content(mapper.writeValueAsString(talentRegisterRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print())
                    .andReturn();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        String talentResponseAsString = talentRegistrationMvcResult.getResponse().getContentAsString();
        RegistrationResponse talantRegistrationResponse =
                mapper.readValue(talentResponseAsString, RegistrationResponse.class);

        MvcResult plannerRegistrationMvcResult = null;
        try {
            plannerRegistrationMvcResult = mockMvc.perform(
                            post(USER_URL)
                                    .content(mapper.writeValueAsString(plannerRegisterRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print())
                    .andReturn();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
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
            exception.printStackTrace();
        }

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
            exception.printStackTrace();
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
            exception.printStackTrace();
        }
    }

    private static EventDetailRequest getEventDetailRequest() throws WrongDateAndTimeFormat {
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
