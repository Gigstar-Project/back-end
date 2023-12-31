package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.User;
import com.rdi.geegstar.dto.requests.RegistrationRequest;
import com.rdi.geegstar.dto.response.RegistrationResponse;
import com.rdi.geegstar.exceptions.EmailConfirmationFailedException;
import com.rdi.geegstar.exceptions.GeegStarException;
import com.rdi.geegstar.exceptions.UserNotFoundException;

import java.util.Optional;

public interface UserService {
    RegistrationResponse registerUser(RegistrationRequest registerRequest);


    Optional<User> findByEmail(String userEmail);

    Object requestEmailConfirmationCode(String userEmail) throws GeegStarException;

    Boolean confirmEmail(String userEmail, String code) throws EmailConfirmationFailedException;

    User findById(Long creativeTalentId) throws UserNotFoundException;
}
