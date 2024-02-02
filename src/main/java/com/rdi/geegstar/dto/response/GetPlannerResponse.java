package com.rdi.geegstar.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class GetPlannerResponse extends GetUserResponse {
    private String companyName;
}
