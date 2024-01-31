package com.rdi.geegstar.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlannerRegistrationRequest extends RegistrationRequest {
    private String eventPlanningCompanyName;
}
