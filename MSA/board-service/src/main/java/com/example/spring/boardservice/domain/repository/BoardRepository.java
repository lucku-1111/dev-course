package com.example.spring.boardservice.domain.repository;

import com.example.spring.boardservice.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
