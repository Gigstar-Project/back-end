package com.rdi.geegstar.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlannerBookingResponse extends UserBookingResponse {
    private BookingResponseTalentResponse talent;
}
