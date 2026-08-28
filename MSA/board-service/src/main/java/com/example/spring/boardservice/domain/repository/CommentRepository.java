package com.example.spring.boardservice.domain.repository;

import com.example.spring.boardservice.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
