package com.rdi.geegstar.services.geegstarimplementations;

import com.rdi.geegstar.data.models.Address;
import com.rdi.geegstar.data.models.Booking;
import com.rdi.geegstar.data.models.EventDetail;
import com.rdi.geegstar.data.models.User;
import com.rdi.geegstar.data.repositories.BookingRepository;
import com.rdi.geegstar.dto.requests.AcceptBookingRequest;
import com.rdi.geegstar.dto.requests.BookingRequest;
import com.rdi.geegstar.dto.requests.EventDetailRequest;
import com.rdi.geegstar.dto.response.AcceptBookingResponse;
import com.rdi.geegstar.dto.response.BookingResponse;
import com.rdi.geegstar.dto.response.DeclineBookingResponse;
import com.rdi.geegstar.exceptions.BookingNotFoundException;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import com.rdi.geegstar.services.BookingService;
import com.rdi.geegstar.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GeegStarBookingService implements BookingService {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final BookingRepository bookingRepository;

    @Override
    public BookingResponse bookTalent(BookingRequest bookCreativeTalentRequest) throws UserNotFoundException {
        EventDetailRequest eventDetailsRequest = bookCreativeTalentRequest.getEventDetailRequest();
        Address eventAddress = modelMapper.map(eventDetailsRequest.getEventAddress(), Address.class);
        EventDetail eventDetail = modelMapper.map(eventDetailsRequest, EventDetail.class);
        eventDetail.setEventAddress(eventAddress);
        Booking savedBooking = getSavedBooking(bookCreativeTalentRequest, eventDetail);
        return modelMapper.map(savedBooking, BookingResponse.class);
    }

    @Override
    public AcceptBookingResponse acceptBooking(AcceptBookingRequest acceptBookingRequest) throws BookingNotFoundException {
        Long bookingId = acceptBookingRequest.getBookingId();
        Booking foundBooking = getBooking(bookingId);
        foundBooking.setAccepted(true);
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

    private Booking getSavedBooking(BookingRequest bookCreativeTalentRequest, EventDetail eventDetail) throws UserNotFoundException {
        User creativeTalent = userService.findById(bookCreativeTalentRequest.getTalent());
        User eventPlanner = userService.findById(bookCreativeTalentRequest.getPlanner());
        Booking booking = new Booking();
        booking.setTalent(List.of(creativeTalent));
        booking.setPlanner(eventPlanner);
        booking.setEventDetail(eventDetail);
        return bookingRepository.save(booking);
    }
}
