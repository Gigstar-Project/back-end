package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.Address;
import com.rdi.geegstar.data.models.Booking;
import com.rdi.geegstar.data.models.EventDetails;
import com.rdi.geegstar.data.models.User;
import com.rdi.geegstar.data.repositories.BookingRepository;
import com.rdi.geegstar.dto.requests.AddressRequest;
import com.rdi.geegstar.dto.requests.BookCreativeTalentRequest;
import com.rdi.geegstar.dto.requests.EventDetailsRequest;
import com.rdi.geegstar.dto.response.BookCreativeTalentResponse;
import com.rdi.geegstar.exceptions.UserNotFoundException;
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
    public BookCreativeTalentResponse bookCreativeTalent(BookCreativeTalentRequest bookCreativeTalentRequest) throws UserNotFoundException {
        EventDetailsRequest eventDetailsRequest = bookCreativeTalentRequest.getEventDetails();
        EventDetails eventDetails = eventDetailsService.create(eventDetailsRequest);
        User creativeTalent = userService.findById(bookCreativeTalentRequest.getCreativeTalentId());
        User eventPlanner = userService.findById(bookCreativeTalentRequest.getEventPlannerId());
        Booking booking = new Booking();
        booking.setCreativeTalent(Collections.singletonList(creativeTalent));
        booking.setEventPlanner(eventPlanner);
        booking.setEventDetails(eventDetails);
        Booking savedBooking = bookingRepository.save(booking);

        return modelMapper.map(savedBooking, BookCreativeTalentResponse.class);
    }
}
