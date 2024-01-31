package com.rdi.geegstar.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdi.geegstar.dto.requests.*;
import com.rdi.geegstar.dto.response.BookingResponse;
import com.rdi.geegstar.dto.response.RegistrationResponse;
import com.rdi.geegstar.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;

import static com.rdi.geegstar.enums.EventType.BIRTHDAY_PARTY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final String URL = "/api/v1/booking";

    @Test
    public void testBookTalent() throws UnsupportedEncodingException, JsonProcessingException {
        Long talentId = registerUser(Role.TALENT);
        Long plannerId = registerUser(Role.PLANNER);
        ObjectMapper mapper = new ObjectMapper();
        BookingRequest bookingRequest = getBookingRequest(plannerId, talentId);
        try {
            mockMvc.perform(
                            post(URL)
                                    .content(mapper.writeValueAsString(bookingRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }
    }

    @Test
    public void testAcceptBooking()
            throws UnsupportedEncodingException, JsonProcessingException {
        Long talentId = registerUser(Role.TALENT);
        Long plannerId = registerUser(Role.PLANNER);
        BookingRequest bookingRequest = getBookingRequest(plannerId, talentId);
        BookingResponse bookingResponse = getBookingResponse(bookingRequest);
        ObjectMapper mapper = new ObjectMapper();
        Long bookingId = bookingResponse.getBookingId();
        AcceptBookingRequest acceptBookingRequest = new AcceptBookingRequest();
        acceptBookingRequest.setBookingId(bookingId);
        acceptBookingRequest.setTalentId(talentId);
        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.patch(String.format("%s/accept", URL))
                                    .content(mapper.writeValueAsString(acceptBookingRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }
    }

    @Test
    public void testDeclineBooking()
            throws UnsupportedEncodingException, JsonProcessingException {
        Long talentId = registerUser(Role.TALENT);
        Long plannerId = registerUser(Role.PLANNER);
        BookingRequest bookingRequest = getBookingRequest(plannerId, talentId);
        BookingResponse bookingResponse = getBookingResponse( bookingRequest);
        Long bookingId = bookingResponse.getBookingId();

        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.patch(String.format("%s/decline", bookingId))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }
    }

    @Test
    public void testGetUserBookings()
            throws UnsupportedEncodingException, JsonProcessingException{
        Long talentId = registerUser(Role.TALENT);
        int pageSize = 1;
        int pageNumber = 1;
        GetUserBookingsRequest getUserBookingsRequest = new GetUserBookingsRequest();
        getUserBookingsRequest.setUserRole(Role.TALENT);
        getUserBookingsRequest.setUserId(talentId);
        getUserBookingsRequest.setPageSize(pageSize);
        getUserBookingsRequest.setPageNumber(pageNumber);
        ObjectMapper mapper = new ObjectMapper();
        try{
            mockMvc.perform(
                            MockMvcRequestBuilders.get(URL)
                                    .content(mapper.writeValueAsString(getUserBookingsRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());

        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }
    }

    private BookingResponse getBookingResponse( BookingRequest bookingRequest)
            throws UnsupportedEncodingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        MvcResult responseOfBooking = null;
        try {
            responseOfBooking = mockMvc.perform(
                            post(URL)
                                    .content(mapper.writeValueAsString(bookingRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }

        assert responseOfBooking != null;
        String content = responseOfBooking.getResponse().getContentAsString();
        return mapper.readValue(content, BookingResponse.class);
    }

    private static BookingRequest getBookingRequest(Long plannerId, Long talentId) {
        EventDetailRequest eventDetailRequest =
                getEventDetailRequest();
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setPlannerId(plannerId);
        bookingRequest.setTalentId(talentId);
        bookingRequest.setEventDetailRequest(eventDetailRequest);
        return bookingRequest;
    }


    private Long registerUser(Role userRole) throws UnsupportedEncodingException, JsonProcessingException {
        String USER_URL = "/api/v1/user";
        ObjectMapper mapper = new ObjectMapper();
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setFirstName("Retnaa");
        registrationRequest.setLastName("Dayok");
        registrationRequest.setEmail("dayokr@gmail.com");
        registrationRequest.setPhoneNumber("07031005737");
        registrationRequest.setPassword("password");
        registrationRequest.setRole(userRole);
        MvcResult userRegistrationResponse = null;
        try {
            userRegistrationResponse = mockMvc.perform(
                            post(USER_URL)
                                    .content(mapper.writeValueAsString(registrationRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }

        assert userRegistrationResponse != null;
        String userResponse = userRegistrationResponse.getResponse().getContentAsString();
        RegistrationResponse registrationResponse = mapper.readValue(userResponse, RegistrationResponse.class);
        return registrationResponse.getId();
    }

    private static EventDetailRequest getEventDetailRequest() {
        EventDetailRequest eventDetailsRequest = new EventDetailRequest();
        eventDetailsRequest.setEventName("Darda's birthday party");
        eventDetailsRequest.setEventType(BIRTHDAY_PARTY);
        eventDetailsRequest.setEventDateAndTime("2023, 01, 04, 10, 30");
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
