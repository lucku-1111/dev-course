package com.example.spring.basicboard.domain.repository;

import com.example.spring.basicboard.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {}
