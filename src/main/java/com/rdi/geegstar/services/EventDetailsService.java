package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.EventDetail;
import com.rdi.geegstar.dto.requests.EventDetailRequest;

public interface EventDetailsService {
    EventDetail create(EventDetailRequest eventDetailsRequest);
}
