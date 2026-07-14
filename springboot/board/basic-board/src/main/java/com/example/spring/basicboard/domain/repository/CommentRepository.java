package com.example.spring.basicboard.domain.repository;

import com.example.spring.basicboard.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
