package com.rdi.geegstar.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TalentBookingResponse extends UserBookingResponse {
    private BookingResponsePlannerResponse planner;
}
