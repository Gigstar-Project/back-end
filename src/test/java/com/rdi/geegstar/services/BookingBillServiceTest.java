package com.rdi.geegstar.services;

import com.rdi.geegstar.dto.requests.BookingBillRequest;
import com.rdi.geegstar.dto.response.BookingBillResponse;
import com.rdi.geegstar.exceptions.BookingNotFoundException;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class BookingBillServiceTest {

    @Autowired
    private BookingBillService bookingBillService;
    @Test
    @Sql("/db/insertUsers.sql")
    public void testCreateBookingBill() throws UserNotFoundException, BookingNotFoundException {
        BookingBillRequest bookingBillRequest = new BookingBillRequest();
        BigDecimal cost = BigDecimal.valueOf(2000000);
        bookingBillRequest.setBookingCost(cost);
        bookingBillRequest.setText("The cost covers for all expenses");
        bookingBillRequest.setPlannerId(103L);
        bookingBillRequest.setTalentId(102L);

        BookingBillResponse bookingBillResponse = bookingBillService.createBookingBill(bookingBillRequest);
        assertThat(bookingBillResponse).isNotNull();
    }
}
