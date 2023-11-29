package com.rdi.geegstar.services;

import com.rdi.geegstar.dto.requests.RegistrationRequest;
import com.rdi.geegstar.dto.response.RegistrationResponse;
import com.rdi.geegstar.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        registerRequest.setRole(Role.CREATIVE_TALENT);
        RegistrationResponse registrationResponse = userService.registerUser(registerRequest);
        assertNotNull(registrationResponse);
    }


    @Test
    public void testEmailConfirmation() {

    }
}
