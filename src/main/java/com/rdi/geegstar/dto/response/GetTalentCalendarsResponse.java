package com.rdi.geegstar.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class GetTalentCalendarsResponse {
    private Long id;
    private Boolean isBooked;
    private LocalDateTime eventDateAndTime;
}
