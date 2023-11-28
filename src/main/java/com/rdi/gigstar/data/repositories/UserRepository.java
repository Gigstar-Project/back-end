package com.rdi.gigstar.data.repositories;

import com.rdi.gigstar.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
