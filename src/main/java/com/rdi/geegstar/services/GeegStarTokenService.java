package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.Token;
import com.rdi.geegstar.data.repositories.TokenRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@AllArgsConstructor
public class GeegStarTokenService implements TokenService{

    private final TokenRepository tokenRepository;
    private final ModelMapper modelMapper;

    @Override
    public Token generateToken(String userEmail) {
        String tokenCode = generateFourDigitTokenCode();
        Token token = new Token();
        token.setEmail(userEmail);
        token.setTokenCode(tokenCode);
        return tokenRepository.save(token);
    }

    private String generateFourDigitTokenCode() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) builder.append(secureRandom.nextInt(10));
        return builder.toString();
    }
}
