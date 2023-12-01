package com.rdi.geegstar.services;

import com.rdi.geegstar.dto.requests.AddressRequest;
import com.rdi.geegstar.dto.requests.BookCreativeTalentRequest;
import com.rdi.geegstar.dto.requests.EventDetailsRequest;
import com.rdi.geegstar.dto.response.BookCreativeTalentResponse;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.rdi.geegstar.enums.EventType.BIRTHDAY_PARTY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class BookTalentServiceTest {

    @Autowired
    private BookTalentService bookTalentService;

    @Test
    @Sql("/db/insertUsers.sql")
    public void testBookingTalent() throws UserNotFoundException {
        BookCreativeTalentRequest bookCreativeTalentRequest = new BookCreativeTalentRequest();
        EventDetailsRequest eventDetailsRequest = getEventDetailsRequest();
        bookCreativeTalentRequest.setCreativeTalentId(102L);
        bookCreativeTalentRequest.setEventDetails(eventDetailsRequest);
        bookCreativeTalentRequest.setEventPlannerId(103L);
        BookCreativeTalentResponse bookCreativeTalentResponse =
                bookTalentService.bookCreativeTalent(bookCreativeTalentRequest);

        assertThat(bookCreativeTalentResponse).isNotNull();
    }

    public static EventDetailsRequest getEventDetailsRequest() {
        EventDetailsRequest eventDetailsRequest = new EventDetailsRequest();

        eventDetailsRequest.setEventName("Darda's birthday party");
        eventDetailsRequest.setEventType(BIRTHDAY_PARTY);
        eventDetailsRequest.setEventDateAndTime(
                LocalDateTime.of(
                        LocalDate.of(2023, 12, 30),
                        LocalTime.of(20, 0, 0)
                )
        );
        AddressRequest addressRequest = getAddressRequest();
        eventDetailsRequest.setEventAddress(addressRequest);
        return eventDetailsRequest;
    }

    public static AddressRequest getAddressRequest() {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setCity("Lagos mainland");
        addressRequest.setCountry("Nigeria");
        addressRequest.setState("Lagos");
        addressRequest.setBuildingNumber(4634L);
        addressRequest.setStreet("Yaba");
        return addressRequest;
    }
}
