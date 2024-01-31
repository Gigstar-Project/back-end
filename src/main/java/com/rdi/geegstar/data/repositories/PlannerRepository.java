package com.rdi.geegstar.data.repositories;

import com.rdi.geegstar.data.models.Planner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlannerRepository extends JpaRepository<Planner, Long> {
}
