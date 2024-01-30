package com.rdi.geegstar.controllers;

import com.rdi.geegstar.dto.requests.GetAllTalentsRequest;
import com.rdi.geegstar.dto.requests.RegistrationRequest;
import com.rdi.geegstar.dto.response.GetAllTalentsResponse;
import com.rdi.geegstar.dto.response.RegistrationResponse;
import com.rdi.geegstar.dto.response.GetUserResponse;
import com.rdi.geegstar.exceptions.GeegStarException;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import com.rdi.geegstar.services.geegstarimplementations.GeegStarUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final GeegStarUserService geegStarUserService;

    @PostMapping
    public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.status(CREATED).body(geegStarUserService.registerUser(registrationRequest));
    }

    @PostMapping("/confirmation/{userEmail}")
    public ResponseEntity<?> requestEmailConfirmationCode(@PathVariable @Email(message = "invalid email address",
                                                                      regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
                                                                              +"[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:"
                                                                              +"[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)"
                                                                              +"+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
                                                              String userEmail) {
        try {
            return ResponseEntity.status(CREATED).body(geegStarUserService.requestEmailConfirmationCode(userEmail));
        } catch (GeegStarException exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }

    @PostMapping("/confirmation/{userEmail}/{tokenCode}")
    public ResponseEntity<?> confirmEmail(@PathVariable String userEmail,
                                          @PathVariable @Pattern(regexp = "^[0-9]{4}$",
                                                  message = "Confirmation code must be a four-digit number")
                                          String tokenCode) {
        try {
            return ResponseEntity.ok(geegStarUserService.confirmEmail(userEmail, tokenCode));

        } catch (GeegStarException exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GetUserResponse> findUserBy(@PathVariable Long id) throws UserNotFoundException {
        return ResponseEntity.ok(geegStarUserService.getUserById(id));
    }

    @GetMapping("/talents")
    public ResponseEntity<List<GetAllTalentsResponse>> getAllTalents(
            @RequestBody GetAllTalentsRequest getAllTalentsRequest) {
        return ResponseEntity.ok(geegStarUserService.getAllTalents(getAllTalentsRequest));
    }
}
