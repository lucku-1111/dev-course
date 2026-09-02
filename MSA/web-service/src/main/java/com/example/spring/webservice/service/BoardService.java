package com.example.spring.webservice.service;

import com.example.spring.webservice.client.BoardClient;
import com.example.spring.webservice.dto.BoardPageResponseDto;
import com.example.spring.webservice.dto.BoardSearchRequestDto;
import com.example.spring.webservice.dto.BoardWithCommentsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardClient boardClient;

    public BoardPageResponseDto searchBoard(String authorization, BoardSearchRequestDto condition, int page, int size) {
        return boardClient.searchBoards(authorization, condition, page, size);
    }

    public BoardWithCommentsResponseDto getBoardWithComments(String authorization, Long id) {
        return boardClient.getBoardWithComments(authorization, id);
    }

}
