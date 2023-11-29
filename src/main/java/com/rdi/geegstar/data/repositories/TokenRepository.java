package com.rdi.geegstar.data.repositories;

import com.rdi.geegstar.data.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
