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
import java.math.BigDecimal;

import static com.rdi.geegstar.enums.EventType.BIRTHDAY_PARTY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingBillControllerTest {

    @Autowired
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateBookingBill() throws WrongDateAndTimeFormat, UnsupportedEncodingException, JsonProcessingException {
        RegistrationRequest talentRegisterRequest = new RegistrationRequest();
        talentRegisterRequest.setFirstName("Retnaa");
        talentRegisterRequest.setLastName("Dayok");
        talentRegisterRequest.setUsername("Darda");
        talentRegisterRequest.setEmail("dayokr@gmail.com");
        talentRegisterRequest.setPhoneNumber("07031005737");
        talentRegisterRequest.setPassword("password");
        talentRegisterRequest.setRole(Role.TALENT);

        RegistrationRequest plannerRegisterRequest = new RegistrationRequest();
        plannerRegisterRequest.setFirstName("Retnaa");
        plannerRegisterRequest.setLastName("Dayok");
        plannerRegisterRequest.setUsername("Darda");
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

        BookingBillRequest bookingBillRequest = new BookingBillRequest();
        bookingBillRequest.setBookingId(bookingResponse.getBookingId());
        BigDecimal bookingCost = BigDecimal.valueOf(200_000);
        bookingBillRequest.setBookingCost(bookingCost);
        bookingBillRequest.setText("This cost covers all expenses");
        bookingBillRequest.setPlannerId(plannerRegistrationResponse.getId());
        bookingBillRequest.setTalentId(talantRegistrationResponse.getId());

        final String BOOKING_BILL_URL = "/api/v1/bill";
        try {
            mockMvc.perform(
                            post(BOOKING_BILL_URL)
                                    .content(mapper.writeValueAsString(bookingBillRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isCreated())
                    .andDo(print());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
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
