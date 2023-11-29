package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.Token;

public interface TokenService {
    Token generateToken(String userEmail);
}
