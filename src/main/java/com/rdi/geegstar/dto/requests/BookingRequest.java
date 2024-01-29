package com.rdi.geegstar.dto.requests;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookingRequest {
    @Valid
    private EventDetailRequest eventDetailRequest;
    private Long plannerId;
    private Long talentId;
}
