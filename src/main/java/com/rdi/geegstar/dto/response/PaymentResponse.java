package com.rdi.geegstar.dto.response;

import com.rdi.geegstar.data.models.User;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class PaymentResponse {
    private Long id;
    private BigDecimal amount;
//    private boolean status;
}
