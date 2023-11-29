package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.User;
import com.rdi.geegstar.dto.requests.RegistrationRequest;
import com.rdi.geegstar.dto.response.RegistrationResponse;

import java.util.Optional;

public interface UserService {
    RegistrationResponse registerUser(RegistrationRequest registerRequest);


    Optional<User> findByEmail(String userEmail);
}
