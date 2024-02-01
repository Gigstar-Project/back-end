package com.rdi.geegstar.controllers;

import com.rdi.geegstar.dto.requests.GetAllTalentsRequest;
import com.rdi.geegstar.dto.requests.PlannerRegistrationRequest;
import com.rdi.geegstar.dto.requests.RegistrationRequest;
import com.rdi.geegstar.dto.requests.TalentRegistrationRequest;
import com.rdi.geegstar.dto.response.GetAllTalentsResponse;
import com.rdi.geegstar.dto.response.RegistrationResponse;
import com.rdi.geegstar.dto.response.GetUserResponse;
import com.rdi.geegstar.exceptions.*;
import com.rdi.geegstar.services.UserService;
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

    private final UserService userService;

    @PostMapping
    public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.status(CREATED).body(userService.registerUser(registrationRequest));
    }

    @PostMapping("/registration/talent")
    public ResponseEntity<RegistrationResponse> registerUser(@Valid @RequestBody TalentRegistrationRequest talentRegistrationRequest) {
        return ResponseEntity.status(CREATED).body(userService.registerUser(talentRegistrationRequest));
    }

    @PostMapping("/registration/planner")
    public ResponseEntity<RegistrationResponse> registerUser(@Valid @RequestBody PlannerRegistrationRequest plannerRegistrationRequest) {
        return ResponseEntity.status(CREATED).body(userService.registerUser(plannerRegistrationRequest));
    }

    @PostMapping("/confirmation/{userEmail}")
    public ResponseEntity<?> requestEmailConfirmationCode(@PathVariable @Email(message = "invalid email address",
                                                                      regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
                                                                              +"[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:"
                                                                              +"[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)"
                                                                              +"+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
                                                              String userEmail)
            throws InValidEmailException, EmailIsTakenException {
        return ResponseEntity.status(CREATED).body(userService.requestEmailConfirmationCode(userEmail));
    }

    @PostMapping("/confirmation/{userEmail}/{tokenCode}")
    public ResponseEntity<?> confirmEmail(@PathVariable String userEmail,
                                          @PathVariable @Pattern(regexp = "^[0-9]{4}$",
                                                  message = "Confirmation code must be a four-digit number")
                                          String tokenCode) throws EmailConfirmationFailedException {
        return ResponseEntity.ok(userService.confirmEmail(userEmail, tokenCode));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable Long id) throws UserNotFoundException {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/talents")
    public ResponseEntity<List<GetAllTalentsResponse>> getAllTalents(
            @RequestBody GetAllTalentsRequest getAllTalentsRequest) {
        return ResponseEntity.ok(userService.getAllTalents(getAllTalentsRequest));
    }
}
