package com.rdi.geegstar.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdi.geegstar.dto.requests.RegistrationRequest;
import com.rdi.geegstar.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegistration() {
        ObjectMapper mapper = new ObjectMapper();
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setFirstName("Retnaa");
        registrationRequest.setLastName("Dayok");
        registrationRequest.setUsername("Darda");
        registrationRequest.setEmail("dayokr@gmail.com");
        registrationRequest.setPhoneNumber("07031005737");
        registrationRequest.setPassword("password");
        registrationRequest.setRole(Role.CREATIVE_TALENT);

        try {
            mockMvc.perform(post("/api/v1/user").content(mapper.writeValueAsString(registrationRequest))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
