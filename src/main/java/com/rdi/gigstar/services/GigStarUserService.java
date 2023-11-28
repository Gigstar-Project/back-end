package com.rdi.gigstar.services;

import com.rdi.gigstar.data.models.User;
import com.rdi.gigstar.data.repositories.UserRepository;
import com.rdi.gigstar.dto.requests.RegistrationRequest;
import com.rdi.gigstar.dto.response.RegistrationResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GigStarUserService implements UserService{

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    @Override
    public RegistrationResponse registerUser(RegistrationRequest registerRequest) {
        User user = modelMapper.map(registerRequest, User.class);
        System.out.println(user.getFirstName());
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, RegistrationResponse.class);
    }
}
