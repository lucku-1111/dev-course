package com.example.spring.board.dto;

import com.example.spring.board.domain.entity.Board;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BoardListResponseDto {
    private List<Board> boards;
    private boolean last;
    private int totalPages;
}
