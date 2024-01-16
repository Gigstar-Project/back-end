package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.EventDetail;
import com.rdi.geegstar.dto.requests.AddressRequest;
import com.rdi.geegstar.dto.requests.EventDetailRequest;
import com.rdi.geegstar.exceptions.WrongDateAndTimeFormat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.rdi.geegstar.enums.EventType.BIRTHDAY_PARTY;
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

    private static EventDetailRequest getEventDetailRequest() throws WrongDateAndTimeFormat {
        EventDetailRequest eventDetailsRequest = new EventDetailRequest();
        eventDetailsRequest.setEventName("Darda's birthday party");
        eventDetailsRequest.setEventType(BIRTHDAY_PARTY);
        eventDetailsRequest.setEventDateAndTime("2023, 12, 04, 10, 30");
        AddressRequest addressRequest = getAddressRequest();
        eventDetailsRequest.setEventAddress(addressRequest);
        return eventDetailsRequest;
    }

    private static AddressRequest getAddressRequest() {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setCity("Lagos mainland");
        addressRequest.setCountry("Nigeria");
        addressRequest.setState("Lagos");
        addressRequest.setBuildingNumber(4634L);
        addressRequest.setStreet("Yaba");
        return addressRequest;
    }
}
