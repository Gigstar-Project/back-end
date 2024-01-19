package com.rdi.geegstar.dto.response;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserBookingResponse {
    private Long id;
    private BookingResponseEventDetailResponse eventDetail;
    private BookingResponseCalenderResponse calendar;
    private boolean isAccepted;
}
