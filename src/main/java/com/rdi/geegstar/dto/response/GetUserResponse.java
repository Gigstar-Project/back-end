package com.rdi.geegstar.dto.response;

import com.rdi.geegstar.enums.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class GetUserResponse {
    private String firstName;
    private String LastName;
    private String profilePicture;
    private Role Role;
    private Long id;
}
