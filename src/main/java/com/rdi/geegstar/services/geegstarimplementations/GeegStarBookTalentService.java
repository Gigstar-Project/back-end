package com.rdi.geegstar.services.geegstarimplementations;

import com.rdi.geegstar.data.models.Booking;
import com.rdi.geegstar.data.models.EventDetail;
import com.rdi.geegstar.data.models.User;
import com.rdi.geegstar.data.repositories.BookingRepository;
import com.rdi.geegstar.dto.requests.BookTalentRequest;
import com.rdi.geegstar.dto.requests.EventDetailRequest;
import com.rdi.geegstar.dto.response.BookCreativeTalentResponse;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import com.rdi.geegstar.services.BookTalentService;
import com.rdi.geegstar.services.EventDetailsService;
import com.rdi.geegstar.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class GeegStarBookTalentService implements BookTalentService {

    private final ModelMapper modelMapper;
    private final EventDetailsService eventDetailsService;
    private final UserService userService;
    private final BookingRepository bookingRepository;

    @Override
    public BookCreativeTalentResponse bookTalent(BookTalentRequest bookCreativeTalentRequest) throws UserNotFoundException {
        EventDetailRequest eventDetailsRequest = bookCreativeTalentRequest.getEventDetails();
        EventDetail eventDetails = eventDetailsService.create(eventDetailsRequest);
        Booking savedBooking = getSavedBooking(bookCreativeTalentRequest, eventDetails);
        return modelMapper.map(savedBooking, BookCreativeTalentResponse.class);
    }

    private Booking getSavedBooking(BookTalentRequest bookCreativeTalentRequest, EventDetail eventDetails) throws UserNotFoundException {
        User creativeTalent = userService.findById(bookCreativeTalentRequest.getCreativeTalentId());
        User eventPlanner = userService.findById(bookCreativeTalentRequest.getEventPlannerId());
        Booking booking = new Booking();
        booking.setCreativeTalent(Collections.singletonList(creativeTalent));
        booking.setEventPlanner(eventPlanner);
        booking.setEventDetails(eventDetails);
        return bookingRepository.save(booking);
    }
}
