package com.rdi.geegstar.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString(callSuper = true)
public class BookingResponseCalenderResponse {
    private Long id;
    private Boolean isBooked;
    private LocalDateTime eventDateAndTime;
}
