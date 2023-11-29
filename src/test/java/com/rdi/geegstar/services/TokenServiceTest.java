package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.Token;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    @Test
    public void testGenerateToken() {
        String userEmail = "dayokr@gmail.com";
        Token token= tokenService.generateToken(userEmail);
        assertNotNull(token);
    }
}
