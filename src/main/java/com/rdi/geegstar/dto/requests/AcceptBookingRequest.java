package com.rdi.geegstar.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptBookingRequest {
    private Long bookingId;
    private Long talentId;
}
