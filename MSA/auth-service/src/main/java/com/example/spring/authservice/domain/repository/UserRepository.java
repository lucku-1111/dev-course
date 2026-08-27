package com.example.spring.authservice.domain.repository;

import com.example.spring.authservice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
