package com.rdi.geegstar.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rdi.geegstar.enums.EventType;
import lombok.Getter;
import lombok.Setter;;

import java.time.LocalDateTime;

@Setter
@Getter
public class EventDetailRequest {
    private String eventName;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime eventDateAndTime;
    private AddressRequest eventAddress;
    private EventType eventType;
}
