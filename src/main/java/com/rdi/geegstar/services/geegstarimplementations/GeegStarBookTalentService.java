package com.rdi.geegstar.services.geegstarimplementations;

import com.rdi.geegstar.data.models.Booking;
import com.rdi.geegstar.data.models.EventDetail;
import com.rdi.geegstar.data.models.User;
import com.rdi.geegstar.data.repositories.BookingRepository;
import com.rdi.geegstar.dto.requests.AcceptBookingRequest;
import com.rdi.geegstar.dto.requests.BookTalentRequest;
import com.rdi.geegstar.dto.requests.EventDetailRequest;
import com.rdi.geegstar.dto.response.AcceptBookingResponse;
import com.rdi.geegstar.dto.response.BookTalentResponse;
import com.rdi.geegstar.dto.response.DeclineBookingResponse;
import com.rdi.geegstar.exceptions.BookingNotFoundException;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import com.rdi.geegstar.services.BookTalentService;
import com.rdi.geegstar.services.EventDetailsService;
import com.rdi.geegstar.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GeegStarBookTalentService implements BookTalentService {

    private final ModelMapper modelMapper;
    private final EventDetailsService eventDetailsService;
    private final UserService userService;
    private final BookingRepository bookingRepository;

    @Override
    public BookTalentResponse bookTalent(BookTalentRequest bookCreativeTalentRequest) throws UserNotFoundException {
        EventDetailRequest eventDetailsRequest = bookCreativeTalentRequest.getEventDetail();
        EventDetail eventDetails = eventDetailsService.create(eventDetailsRequest);
        Booking savedBooking = getSavedBooking(bookCreativeTalentRequest, eventDetails);
        return modelMapper.map(savedBooking, BookTalentResponse.class);
    }

    @Override
    public AcceptBookingResponse acceptBooking(AcceptBookingRequest acceptBookingRequest) throws BookingNotFoundException {
        Long bookingId = acceptBookingRequest.getBookingId();
        Booking foundBooking = getBooking(bookingId);
        foundBooking.setAccepted(acceptBookingRequest.getBookingReply());
        bookingRepository.save(foundBooking);
        //Create a bill calling the bookingBill service
        return new AcceptBookingResponse("Successful");
    }

    private Booking getBooking(Long bookingId) throws BookingNotFoundException {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(String.format("The booking with %d id is not found", bookingId)));
    }

    @Override
    public DeclineBookingResponse declineBooking(Long bookingId) throws BookingNotFoundException {
        Booking foundBooking = getBooking(bookingId);
        foundBooking.setAccepted(false);
        bookingRepository.save(foundBooking);
        return new DeclineBookingResponse("Successful");
    }

    private Booking getSavedBooking(BookTalentRequest bookCreativeTalentRequest, EventDetail eventDetails) throws UserNotFoundException {
        User creativeTalent = userService.findById(bookCreativeTalentRequest.getTalent());
        User eventPlanner = userService.findById(bookCreativeTalentRequest.getPlanner());
        Booking booking = new Booking();
        booking.setTalent(List.of(creativeTalent));
        booking.setPlanner(eventPlanner);
        booking.setEventDetail(eventDetails);
        return bookingRepository.save(booking);
    }
}
