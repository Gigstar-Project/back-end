package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.EventDetail;
import com.rdi.geegstar.dto.requests.EventDetailRequest;
import com.rdi.geegstar.exceptions.WrongDateAndTimeFormat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.rdi.geegstar.services.BookServiceTest.getEventDetailRequest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class EventDetailServiceTest {

    @Autowired
    private EventDetailsService eventDetailsService;

    @Test
    public void testCreateEventDetails() throws WrongDateAndTimeFormat {
        EventDetailRequest eventDetailsRequest = getEventDetailRequest();
        EventDetail eventDetails = eventDetailsService.create(eventDetailsRequest);
        assertThat(eventDetails).isNotNull();
    }
}
