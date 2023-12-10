package com.rdi.geegstar.data.repositories;

import com.rdi.geegstar.data.models.User;
import com.rdi.geegstar.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String userEmail);

    Optional<User> getAllBy(Role role);
}
