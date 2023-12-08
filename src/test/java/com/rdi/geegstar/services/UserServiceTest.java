package com.rdi.geegstar.services;

import com.rdi.geegstar.dto.requests.RegistrationRequest;
import com.rdi.geegstar.dto.response.UserDisplayDetails;
import com.rdi.geegstar.dto.response.RegistrationResponse;
import com.rdi.geegstar.enums.Role;
import com.rdi.geegstar.exceptions.EmailConfirmationFailedException;
import com.rdi.geegstar.exceptions.GeegStarException;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;


    @Test
    public void testRegister(){
        RegistrationRequest registerRequest = new RegistrationRequest();
        registerRequest.setFirstName("Retnaa");
        registerRequest.setLastName("Dayok");
        registerRequest.setUsername("Darda");
        registerRequest.setEmail("dayokr@gmail.com");
        registerRequest.setPhoneNumber("07031005737");
        registerRequest.setPassword("password");
        registerRequest.setRole(Role.TALENT);
        RegistrationResponse registrationResponse = userService.registerUser(registerRequest);
        assertNotNull(registrationResponse);
    }


    @Test
    public void testRequestConfirmationCode() throws GeegStarException {
        String userEmail = "ebukizy1@gmail.com";
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
    public void testFindUserById() throws UserNotFoundException {
        RegistrationRequest registerRequest = new RegistrationRequest();
        registerRequest.setFirstName("Retnaa");
        registerRequest.setLastName("Dayok");
        registerRequest.setUsername("Darda");
        registerRequest.setEmail("dayr@gmail.com");
        registerRequest.setPhoneNumber("07031005737");
        registerRequest.setPassword("password");
        registerRequest.setRole(Role.TALENT);
        RegistrationResponse registrationResponse = userService.registerUser(registerRequest);
        assertNotNull(registrationResponse);

        UserDisplayDetails userDisplayDetails = userService.getUserDetails(registrationResponse.getId());

        assertThat(userDisplayDetails).isNotNull();
    }

    @Test
    public void testFindAllTalents() {

    }
}
