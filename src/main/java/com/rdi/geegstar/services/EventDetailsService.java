package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.EventDetails;
import com.rdi.geegstar.dto.requests.EventDetailsRequest;

public interface EventDetailsService {
    EventDetails create(EventDetailsRequest eventDetailsRequest);
}
