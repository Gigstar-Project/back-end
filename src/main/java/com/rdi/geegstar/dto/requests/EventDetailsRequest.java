package com.rdi.geegstar.dto.requests;

import com.rdi.geegstar.data.models.Address;
import com.rdi.geegstar.enums.EventType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class EventDetailsRequest {
    private String eventName;
    private LocalDateTime eventDateAndTime;
    private AddressRequest eventAddress;
    private EventType eventType;
}
