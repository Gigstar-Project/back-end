package com.rdi.geegstar.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdi.geegstar.dto.requests.GetAllTalentsRequest;
import com.rdi.geegstar.dto.requests.PortfolioRequest;
import com.rdi.geegstar.dto.requests.RegistrationRequest;
import com.rdi.geegstar.dto.requests.TalentRegistrationRequest;
import com.rdi.geegstar.enums.Role;
import com.rdi.geegstar.enums.TalentCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private static final String USER_URL = "/api/v1/user";


    @Test
    public void testTalentRegistration() {
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

        try {
            mockMvc.perform(
                            post(String.format("%s/registration/talent", USER_URL))
                                    .content(mapper.writeValueAsString(talentRegistrationRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.info("Error ", exception);
        }
    }

    @Test
    public void testRegistration() {
        ObjectMapper mapper = new ObjectMapper();
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setFirstName("Retnaa");
        registrationRequest.setLastName("Dayok");
        registrationRequest.setEmail("dayokr@gmail.com");
        registrationRequest.setPhoneNumber("07031005737");
        registrationRequest.setPassword("password");
        registrationRequest.setRole(Role.TALENT);

        try {
            mockMvc.perform(
                            post(USER_URL)
                                    .content(mapper.writeValueAsString(registrationRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }
    }

    @Test
    public void testRequestConfirmationCode() {
        try {
            String userEmail = "dayokr@gmail.com";
            mockMvc.perform(
                            post(String.format("%s/confirmation/%s", USER_URL, userEmail))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isCreated())
                    .andDo(print());
        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }
    }

    @Test
    @Sql("/db/insert.sql")
    public void testConfirmingEmail() {
        String userEmail = "john499@qianhost.com";
        String code = "4532";
        try {
            mockMvc.perform(
                            post(String.format("%s/confirmation/%s/%s", USER_URL, userEmail,code))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }
    }

    @Test
    @Sql("/db/insertUsers.sql")
    public void testGetUserById() {
        Long userId = 104L;
        try{
            mockMvc.perform(
                            MockMvcRequestBuilders.get(String.format("%s/%d", USER_URL, userId))
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }
    }

    @Test
    @Sql("/db/insertUsers.sql")
    public void testGetAllTalents() {
        ObjectMapper mapper = new ObjectMapper();
        GetAllTalentsRequest getAllTalentsRequest = new GetAllTalentsRequest();
        int pageNumber = 1;
        int pageSize = 2;
        getAllTalentsRequest.setPageNumber(pageNumber);
        getAllTalentsRequest.setPageSize(pageSize);
        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.get(String.format("%s/talents", USER_URL))
                                    .content(mapper.writeValueAsString(getAllTalentsRequest))
                                    .contentType(MediaType.APPLICATION_JSON)

                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());

        } catch (Exception exception) {
            log.info("Error :: ", exception);
        }
    }


}
