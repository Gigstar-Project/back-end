package com.rdi.geegstar.data.repositories;

import com.rdi.geegstar.data.models.Booking;
import com.rdi.geegstar.data.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BookingRepository extends JpaRepository<Booking, Long> {
    Page<Booking> findAllByTalent(User talent, Pageable pageable);

    Page<Booking> findAllByPlanner(User planer, Pageable pageable);

    Page<Booking> findAllByTalentId(Long talentId, Pageable pageable);
    @Query("SELECT b FROM Booking b WHERE b.talent.id = :talentId AND b.eventDetail.eventDateAndTime >= CURRENT_DATE")
    Page<Booking> findAllByTalentIdAndBookingDateAfterToday(@Param("talentId") Long talentId, Pageable pageable);

    @Query("SELECT b FROM Booking b WHERE b.planner.id = :plannerId AND b.eventDetail.eventDateAndTime >= CURRENT_DATE")
    Page<Booking> findAllByPlannerIdAndBookingDateAfterToday(@Param("plannerId") Long plannerId, Pageable pageable);
}
