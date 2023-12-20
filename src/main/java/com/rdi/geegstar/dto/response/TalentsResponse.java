package com.rdi.geegstar.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TalentsResponse {
    private String displayName;
    private List<String> talentCatalog;
    private String profilePicture;
    private String userId;
}
