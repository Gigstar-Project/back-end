package com.rdi.gigstar.services;

import com.rdi.gigstar.dto.requests.RegistrationRequest;
import com.rdi.gigstar.dto.response.RegistrationResponse;

public interface UserService {
    RegistrationResponse registerUser(RegistrationRequest registerRequest);
}
