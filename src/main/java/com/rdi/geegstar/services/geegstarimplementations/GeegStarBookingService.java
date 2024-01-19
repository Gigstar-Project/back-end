package com.rdi.geegstar.services.geegstarimplementations;

import com.rdi.geegstar.data.models.*;
import com.rdi.geegstar.data.repositories.BookingRepository;
import com.rdi.geegstar.dto.requests.*;
import com.rdi.geegstar.dto.response.*;
import com.rdi.geegstar.enums.Role;
import com.rdi.geegstar.exceptions.BookingNotFoundException;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import com.rdi.geegstar.services.BookingService;
import com.rdi.geegstar.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class GeegStarBookingService implements BookingService {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final BookingRepository bookingRepository;

    @Override
    public BookingResponse bookTalent(BookingRequest bookCreativeTalentRequest)
            throws UserNotFoundException {
        EventDetailRequest eventDetailsRequest = bookCreativeTalentRequest.getEventDetailRequest();
        Address eventAddress = modelMapper.map(eventDetailsRequest.getEventAddress(), Address.class);
        EventDetail eventDetail = modelMapper.map(eventDetailsRequest, EventDetail.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy, MM, dd, HH, mm");
        eventDetail.setEventDateAndTime(LocalDateTime.parse(eventDetailsRequest.getEventDateAndTime(), formatter));
        eventDetail.setEventAddress(eventAddress);
        User talent = userService.findUserById(bookCreativeTalentRequest.getTalentId());
        User planner = userService.findUserById(bookCreativeTalentRequest.getPlannerId());
        Calendar calendar = new Calendar();
        calendar.setTalent(talent);
        calendar.setEventDateAndTime(LocalDateTime.parse(eventDetailsRequest.getEventDateAndTime(), formatter));
        Booking booking = new Booking();
        booking.setTalent(talent);
        booking.setPlanner(planner);
        booking.setEventDetail(eventDetail);
        booking.setCalendar(calendar);
        Booking savedBooking = bookingRepository.save(booking);
        return modelMapper.map(savedBooking, BookingResponse.class);
    }

    @Override
    public AcceptBookingResponse acceptBooking (AcceptBookingRequest acceptBookingRequest)
            throws BookingNotFoundException, UserNotFoundException {
        Long bookingId = acceptBookingRequest.getBookingId();
        Booking foundBooking = findBookingById(bookingId);
        Calendar calendar = foundBooking.getCalendar();
        Long bookedTalentId = foundBooking.getTalent().getId();
        Long givenTalentIdFromRequest = acceptBookingRequest.getTalentId();
        if (Objects.equals(bookedTalentId, givenTalentIdFromRequest)) {
            foundBooking.setAccepted(true);
            calendar.setIsBooked(true);
            bookingRepository.save(foundBooking);
            return new AcceptBookingResponse("Successful");
        }
        return new AcceptBookingResponse("Un-Successful");
    }

    public Booking findBookingById(Long bookingId) throws BookingNotFoundException {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new BookingNotFoundException(String.format("The booking with %d id is not found", bookingId)));
    }

    @Override
    public List<PlannerBookingResponse> getPlannerBookings(Long plannerId) {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .filter(booking ->
                        Objects.equals(booking.getPlanner().getId(), plannerId))
                .map(booking -> {
                    User talent = booking.getTalent();
                    BookingResponseTalentResponse bookingResponseTalentResponse =
                            modelMapper.map(talent, BookingResponseTalentResponse.class);
                    EventDetail eventDetail = booking.getEventDetail();
                     Address address = eventDetail.getEventAddress();
                    BookingResponseEventDetailResponse bookingResponseEventDetailResponse =
                            modelMapper.map(eventDetail, BookingResponseEventDetailResponse.class);
                    BookingResponseAddressResponse bookingResponseAddressResponse =
                            modelMapper.map(address, BookingResponseAddressResponse.class);bookingResponseEventDetailResponse.setEventAddress(bookingResponseAddressResponse);
                    Calendar calendar = booking.getCalendar();
                    BookingResponseCalenderResponse bookingResponseCalenderResponse =
                            modelMapper.map(calendar, BookingResponseCalenderResponse.class);
                    PlannerBookingResponse plannerBookingResponse = new PlannerBookingResponse();
                    plannerBookingResponse.setId(booking.getId());
                    plannerBookingResponse.setTalent(bookingResponseTalentResponse);
                    plannerBookingResponse.setEventDetail(bookingResponseEventDetailResponse);
                    plannerBookingResponse.setCalendar(bookingResponseCalenderResponse);
                    plannerBookingResponse.setAccepted(booking.isAccepted());
                    return plannerBookingResponse;
                })
                .toList();
    }

    @Override
    public List<TalentBookingResponse> getTalentBookings(Long talentId) {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .filter(booking -> Objects.equals(booking.getTalent().getId(), talentId))
                .map(booking -> {
                    User planner = booking.getPlanner();
                    BookingResponsePlannerResponse bookingResponsePlannerResponse =
                            modelMapper.map(planner, BookingResponsePlannerResponse.class);
                    EventDetail eventDetail = booking.getEventDetail();
                    Address address = eventDetail.getEventAddress();
                    BookingResponseEventDetailResponse bookingResponseEventDetailResponse =
                            modelMapper.map(eventDetail, BookingResponseEventDetailResponse.class);
                    BookingResponseAddressResponse bookingResponseAddressResponse =
                            modelMapper.map(address, BookingResponseAddressResponse.class);
                    bookingResponseEventDetailResponse.setEventAddress(bookingResponseAddressResponse);
                    Calendar calendar = booking.getCalendar();
                    BookingResponseCalenderResponse bookingResponseCalenderResponse =
                            modelMapper.map(calendar, BookingResponseCalenderResponse.class);
                    TalentBookingResponse talentBookingResponse = new TalentBookingResponse();
                    talentBookingResponse.setPlanner(bookingResponsePlannerResponse);
                    talentBookingResponse.setEventDetail(bookingResponseEventDetailResponse);
                    talentBookingResponse.setCalendar(bookingResponseCalenderResponse);
                    talentBookingResponse.setId(booking.getId());
                    talentBookingResponse.setAccepted(booking.isAccepted());
                    return talentBookingResponse;
                })
                .toList();
    }

    @Override
    public DeclineBookingResponse declineBooking(Long bookingId) throws BookingNotFoundException {
        Booking foundBooking = findBookingById(bookingId);
        foundBooking.setAccepted(false);
        bookingRepository.save(foundBooking);
        return new DeclineBookingResponse("Successful");
    }

}
