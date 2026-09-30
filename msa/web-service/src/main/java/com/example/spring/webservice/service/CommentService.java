package com.example.spring.webservice.service;

import com.example.spring.webservice.client.BoardClient;
import com.example.spring.webservice.dto.CommentWriteRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardClient boardClient;

    public void addComment(
            String authorization,
            long boardId,
            CommentWriteRequestDto requestDto
    ){
        boardClient.addComment(authorization, boardId, requestDto);
    }

}
