package com.rdi.geegstar.dto.requests;

import com.rdi.geegstar.enums.EventType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class EventDetailRequest {
    private String eventName;
    @Pattern(regexp = "\\d{4}, \\d{2}, \\d{2}, \\d{2}, \\d{2}",
            message = "Date and time must be in the format YYYY, MM, DD HH, mm. For example: 2023, 12, 24, 16, 30")
    private String eventDateAndTime;
    private AddressRequest eventAddress;
    @NotBlank(message = "You need to select the type of event")
    @Enumerated(EnumType.STRING)
    private EventType eventType;
}
