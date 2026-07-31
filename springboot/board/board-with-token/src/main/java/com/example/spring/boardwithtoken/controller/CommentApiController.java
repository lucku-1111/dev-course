package com.example.spring.boardwithtoken.controller;

import com.example.spring.boardwithtoken.dto.CommentWriteRequestDto;
import com.example.spring.boardwithtoken.service.CommentService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "댓글 API", description = "게시글에 댓글 달기")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/{boardId}/comments")
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping
    public void addComment(
            @Parameter(description = "댓글을 달 게시글 id", example = "1")
            @PathVariable long boardId,
            @RequestBody CommentWriteRequestDto dto
    ) {
        commentService.addComment(boardId, dto);
    }
}
