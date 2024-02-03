package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.User;
import com.rdi.geegstar.dto.requests.GetAllTalentsRequest;
import com.rdi.geegstar.dto.requests.PlannerRegistrationRequest;
import com.rdi.geegstar.dto.requests.RegistrationRequest;
import com.rdi.geegstar.dto.requests.TalentRegistrationRequest;
import com.rdi.geegstar.dto.response.GetAllTalentsResponse;
import com.rdi.geegstar.dto.response.GetUserResponse;
import com.rdi.geegstar.dto.response.RegistrationResponse;
import com.rdi.geegstar.exceptions.*;

import java.util.List;

public interface UserService {

    RegistrationResponse registerUser(PlannerRegistrationRequest plannerRegistrationRequest);
    RegistrationResponse registerUser(TalentRegistrationRequest talentRegistrationRequest);

    String requestEmailConfirmationCode(String userEmail) throws InValidEmailException, EmailIsTakenException;

    Boolean confirmEmail(String userEmail, String code) throws EmailConfirmationFailedException;

    User findUserById(Long userId) throws UserNotFoundException;

    GetUserResponse getUserById(Long userId) throws UserNotFoundException;

    List<GetAllTalentsResponse> getAllTalents(GetAllTalentsRequest getAllTalentsRequest);
}
