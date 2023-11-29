package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.User;
import com.rdi.geegstar.data.repositories.UserRepository;
import com.rdi.geegstar.dto.requests.RegistrationRequest;
import com.rdi.geegstar.dto.response.RegistrationResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@AllArgsConstructor
public class GeegStarUserService implements UserService{

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    @Override
    public RegistrationResponse registerUser(RegistrationRequest registerRequest) {
        User user = modelMapper.map(registerRequest, User.class);
        System.out.println(user.getFirstName());
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, RegistrationResponse.class);
    }

    @Override
    public Optional<User> findByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

}
