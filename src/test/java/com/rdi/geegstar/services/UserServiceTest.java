package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.Portfolio;
import com.rdi.geegstar.dto.requests.*;
import com.rdi.geegstar.dto.response.GetAllTalentsResponse;
import com.rdi.geegstar.dto.response.GetUserResponse;
import com.rdi.geegstar.dto.response.RegistrationResponse;
import com.rdi.geegstar.enums.Role;
import com.rdi.geegstar.enums.TalentCategory;
import com.rdi.geegstar.exceptions.EmailConfirmationFailedException;
import com.rdi.geegstar.exceptions.GeegStarException;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;


    @Test
    public void testTalentRegistration() {
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

        RegistrationResponse registrationResponse = userService.registerUser(talentRegistrationRequest);
        assertThat(registrationResponse).isNotNull();
    }

    @Test
    public void testPlannerRegistration() {
        PlannerRegistrationRequest plannerRegistrationRequest = new PlannerRegistrationRequest();
        plannerRegistrationRequest.setFirstName("Retnaa");
        plannerRegistrationRequest.setLastName("Dayok");
        plannerRegistrationRequest.setEmail("dayokr@gmail.com");
        plannerRegistrationRequest.setPassword("password");
        plannerRegistrationRequest.setPhoneNumber("07031005737");
        plannerRegistrationRequest.setEventPlanningCompanyName("StarEvents Inc");

        RegistrationResponse registrationResponse = userService.registerUser(plannerRegistrationRequest);
        assertThat(registrationResponse).isNotNull();
    }


    @Test
    public void testRegister(){
        RegistrationRequest registerRequest = new RegistrationRequest();
        registerRequest.setFirstName("Retnaa");
        registerRequest.setLastName("Dayok");

        registerRequest.setEmail("dayokr@gmail.com");
        registerRequest.setPhoneNumber("07031005737");
        registerRequest.setPassword("password");
        registerRequest.setRole(Role.TALENT);
        RegistrationResponse registrationResponse = userService.registerUser(registerRequest);
        assertNotNull(registrationResponse);
    }


    @Test
    public void testRequestConfirmationCode() throws GeegStarException {
        String userEmail = "max_ret@yahoo.com";
        var response = userService.requestEmailConfirmationCode(userEmail);
        assertNotNull(response);
    }


    @Test
    @Sql("/db/insert.sql")
    public void testConfirmingEmail() throws EmailConfirmationFailedException {
        String userEmail = "john499@qianhost.com";
        String code = "4532";
        var response = userService.confirmEmail(userEmail, code);
        assertTrue(response);
    }

    @Test
    public void testGetUserById() throws UserNotFoundException {
        RegistrationRequest registerRequest = new RegistrationRequest();
        registerRequest.setFirstName("Retnaa");
        registerRequest.setLastName("Dayok");
        registerRequest.setEmail("dayr@gmail.com");
        registerRequest.setPhoneNumber("07031005737");
        registerRequest.setPassword("password");
        registerRequest.setRole(Role.TALENT);
        RegistrationResponse registrationResponse = userService.registerUser(registerRequest);
        assertNotNull(registrationResponse);

        GetUserResponse userDisplayDetails = userService.getUserById(registrationResponse.getId());

        assertThat(userDisplayDetails).isNotNull();
    }

    @Test
    @Sql("/db/insertUsers.sql")
    public void testGetAllTalents() {
        GetAllTalentsRequest getAllTalentRequest = new GetAllTalentsRequest();
        int pageNumber = 1;
        int pageSize = 3;
        getAllTalentRequest.setPageNumber(pageNumber);
        getAllTalentRequest.setPageSize(pageSize);
        List<GetAllTalentsResponse> talentsResponseList = userService.getAllTalents(getAllTalentRequest);
        System.out.printf(talentsResponseList.toString());
        assertThat(talentsResponseList).isNotNull();
    }

}
