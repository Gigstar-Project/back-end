package com.rdi.geegstar.data.repositories;

import com.rdi.geegstar.data.models.Booking;
import com.rdi.geegstar.data.models.BookingBill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingBillRepository extends JpaRepository<BookingBill, Long> {

    Optional<BookingBill> findByBookingId(Long bookingId);
}
