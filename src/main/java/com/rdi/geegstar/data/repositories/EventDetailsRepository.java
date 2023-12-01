package com.rdi.geegstar.data.repositories;

import com.rdi.geegstar.data.models.EventDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDetailsRepository extends JpaRepository<EventDetails, Long> {
}
