package com.rdi.geegstar.dto.requests;

import com.rdi.geegstar.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationRequest {


    @NotBlank(message = "You must input a first name")
    @Size(min = 3, message = "Your name must be at least 3 letters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters (no spaces allowed)")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters (no spaces allowed)")
    @NotBlank(message = "You must input a last name")
    @Size(min = 3, message = "Your name must be at least 3 letters")
    private String lastName;

    @NotBlank(message = "You need to enter a display name")
    @Size(min = 3, message =  "Display name must be at least 3 characters")
    private String displayName;

    @Email(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message = "invalid email address")
    private String email;

    @Size(min = 8, message =  "Password must be not less than 8 characters long")
    private String password;

    private String bio;
    @Pattern(regexp = "(?:(?:(?:\\+?234(?:\\h1)?|01)\\h*)?(?:\\(\\d{3}\\)|\\d{3})|\\d{4})(?:\\W*\\d{3})?\\W*\\d{4}(?!\\d)",
            message = "Please enter a valid phone number")
    private String phoneNumber;

    private Role role;
}
