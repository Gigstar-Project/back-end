package com.rdi.geegstar.data.repositories;

import com.rdi.geegstar.data.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
