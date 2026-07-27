package com.example.spring.formlogin.domain.repository;

import com.example.spring.formlogin.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
