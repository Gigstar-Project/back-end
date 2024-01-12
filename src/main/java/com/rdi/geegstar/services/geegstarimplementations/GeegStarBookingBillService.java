package com.rdi.geegstar.services.geegstarimplementations;

import com.rdi.geegstar.data.models.BookingBill;
import com.rdi.geegstar.data.models.User;
import com.rdi.geegstar.data.repositories.BookingBillRepository;
import com.rdi.geegstar.dto.requests.BookingBillRequest;
import com.rdi.geegstar.dto.response.BookingBillResponse;
import com.rdi.geegstar.dto.response.BookingResponse;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import com.rdi.geegstar.services.BookingBillService;
import com.rdi.geegstar.services.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeegStarBookingBillService implements BookingBillService {

    private final BookingBillRepository bookingBillRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public BookingBillResponse createBookingBill(BookingBillRequest bookingBillRequest) throws UserNotFoundException {
        User talent = userService.findById(bookingBillRequest.getTalentId());
        User planner = userService.findById(bookingBillRequest.getPlannerId());
        BookingBill bookingBill = new BookingBill();
        bookingBill.setTalent(talent);
        bookingBill.setPlanner(planner);
        bookingBill.setText(bookingBillRequest.getText());
        BookingBill savedBookingBill = bookingBillRepository.save(bookingBill);
        return modelMapper.map(savedBookingBill, BookingBillResponse.class);
    }

}
