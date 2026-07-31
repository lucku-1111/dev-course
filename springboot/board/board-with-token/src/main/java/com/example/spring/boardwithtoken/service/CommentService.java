package com.example.spring.boardwithtoken.service;

import com.example.spring.boardwithtoken.domain.entity.Board;
import com.example.spring.boardwithtoken.domain.entity.Comment;
import com.example.spring.boardwithtoken.domain.repository.BoardRepository;
import com.example.spring.boardwithtoken.domain.repository.CommentRepository;
import com.example.spring.boardwithtoken.dto.CommentWriteRequestDto;
import com.example.spring.boardwithtoken.exception.BoardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void addComment(Long boardId, CommentWriteRequestDto dto, String requestUserId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("게시글을 찾을 수 없습니다. id: " + boardId));

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .userId(requestUserId)
                .board(board)
                .created(LocalDateTime.now())
                .build();
        commentRepository.save(comment);
    }
}
