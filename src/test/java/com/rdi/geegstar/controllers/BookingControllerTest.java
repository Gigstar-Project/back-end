package com.rdi.geegstar.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdi.geegstar.dto.requests.BookingRequest;
import com.rdi.geegstar.dto.requests.EventDetailRequest;
import com.rdi.geegstar.exceptions.WrongDateAndTimeFormat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static com.rdi.geegstar.services.BookServiceTest.getEventDetailRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql("/db/insertUsers.sql")
    public void testBookTalent() throws WrongDateAndTimeFormat {
        ObjectMapper mapper = new ObjectMapper();
        EventDetailRequest eventDetailRequest =
                getEventDetailRequest();
        BookingRequest bookTalentRequest = new BookingRequest();
        bookTalentRequest.setPlanner(103L);
        bookTalentRequest.setTalent(102L);
        bookTalentRequest.setEventDetailRequest(eventDetailRequest);
        try {
            mockMvc.perform(
                            post("/api/v1/booking")
                                    .content(mapper.writeValueAsString(bookTalentRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
