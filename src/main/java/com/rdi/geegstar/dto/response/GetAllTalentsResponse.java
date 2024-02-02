package com.rdi.geegstar.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class GetAllTalentsResponse {
    private String displayName;
    private Long id;
    private String profilePicture;
    private String bio;
}
