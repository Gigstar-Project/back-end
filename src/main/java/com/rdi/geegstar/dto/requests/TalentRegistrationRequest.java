package com.rdi.geegstar.dto.requests;

import com.rdi.geegstar.enums.TalentCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TalentRegistrationRequest extends RegistrationRequest {

    @NotBlank(message = "You need to enter a display name")
    @Size(min = 3, message =  "Display name must be at least 3 characters")
    private String displayName;

    @NotBlank(message = "A talent needs a bio to briefly give their background")
    private String bio;

    private TalentCategory talentCategory;
    private PortfolioRequest portfolioRequest;

}
