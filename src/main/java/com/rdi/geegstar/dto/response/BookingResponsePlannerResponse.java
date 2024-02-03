package com.rdi.geegstar.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class BookingResponsePlannerResponse {
    private Long id;
    private String companyName;
    private String firstName;
    private String LastName;
    private String profilePicture;
}
