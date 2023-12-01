package com.rdi.geegstar.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookTalentRequest {
    private EventDetailRequest eventDetails;
    private Long eventPlannerId;
    private Long creativeTalentId;
}
