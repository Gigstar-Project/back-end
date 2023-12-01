package com.rdi.geegstar.dto.requests;

import com.rdi.geegstar.data.models.EventDetails;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookCreativeTalentRequest {
    private EventDetailsRequest eventDetails;
    private Long eventPlannerId;
    private Long creativeTalentId;
}
