package com.rdi.geegstar.dto.response;

import com.rdi.geegstar.enums.TalentCategory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class GetTalentResponse extends GetUserResponse {
    private String bio;
    private String displayName;
    private PortfolioResponse portfolioResponse;
    private TalentCategory talentCategory;
}
