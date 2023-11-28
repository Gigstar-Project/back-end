package com.rdi.gigstar.controllers;

import com.rdi.gigstar.dto.requests.RegistrationRequest;
import com.rdi.gigstar.dto.response.RegistrationResponse;
import com.rdi.gigstar.services.GigStarUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final GigStarUserService gigStarUserService;

    @PostMapping
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.status(CREATED).body(gigStarUserService.registerUser(registrationRequest));
    }
}
