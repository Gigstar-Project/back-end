package com.rdi.geegstar.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class GetBookingBillDetailsResponse {
    private Long id;
    private String text;
    private BigDecimal amount;
    private boolean isPaid;
}
