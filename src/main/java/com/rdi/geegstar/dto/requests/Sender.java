package com.rdi.geegstar.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class Sender {
    private final String email;
    private final String name;
}
