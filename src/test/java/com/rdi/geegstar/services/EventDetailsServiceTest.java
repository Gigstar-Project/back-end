package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.EventDetails;
import com.rdi.geegstar.dto.requests.EventDetailsRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.rdi.geegstar.services.BookTalentServiceTest.getEventDetailsRequest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class EventDetailsServiceTest {

    @Autowired
    private EventDetailsService eventDetailsService;

    @Test
    public void testCreateEventDetails() {
        EventDetailsRequest eventDetailsRequest = getEventDetailsRequest();
        EventDetails eventDetails = eventDetailsService.create(eventDetailsRequest);
        assertThat(eventDetails).isNotNull();
        System.out.println(eventDetails.getId());
        System.out.println(eventDetails.getEventAddress().getId());
    }
}
