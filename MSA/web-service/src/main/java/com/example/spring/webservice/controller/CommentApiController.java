package com.example.spring.webservice.controller;

import com.example.spring.webservice.dto.CommentWriteRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api1/boards/{boardId}/comments")
public class CommentApiController {

    @PostMapping
    public void addComment(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable("boardId") long boardId,
            @RequestBody CommentWriteRequestDto dto
            ) {

    }
}
