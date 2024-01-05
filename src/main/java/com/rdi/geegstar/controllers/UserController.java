package com.rdi.geegstar.controllers;

import com.rdi.geegstar.dto.requests.RegistrationRequest;
import com.rdi.geegstar.dto.response.RegistrationResponse;
import com.rdi.geegstar.dto.response.GetUserResponse;
import com.rdi.geegstar.exceptions.GeegStarException;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import com.rdi.geegstar.services.geegstarimplementations.GeegStarUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final GeegStarUserService geegStarUserService;

    @PostMapping
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.status(CREATED).body(geegStarUserService.registerUser(registrationRequest));
    }

    @PostMapping("/confirmation/{userEmail}")
    public ResponseEntity<?> requestEmailConfirmationCode(@PathVariable String userEmail) {
        try {
            return ResponseEntity.status(CREATED).body(geegStarUserService.requestEmailConfirmationCode(userEmail));
        } catch (GeegStarException exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }

    @PostMapping("/confirmation/{userEmail}/{tokenCode}")
    public ResponseEntity<?> confirmEmail(@PathVariable String userEmail, @PathVariable String tokenCode) {
        try {
            return ResponseEntity.ok(geegStarUserService.confirmEmail(userEmail, tokenCode));

        } catch (GeegStarException exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GetUserResponse> findUserBy(@PathVariable Long id) {
        try {
            System.out.println("I done show here oh");
            return ResponseEntity.ok(geegStarUserService.getUserById(id));
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
