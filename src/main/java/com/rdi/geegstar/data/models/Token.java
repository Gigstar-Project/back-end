package com.rdi.geegstar.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
public class Token {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String email;
    private String tokenCode;
    private LocalDateTime createdAt=LocalDateTime.now();
    private LocalDateTime expiresAt=LocalDateTime.now().plusMinutes(10);
    private Boolean isConfirmed;
}
