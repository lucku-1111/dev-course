package com.example.spring.boardservice.controller;

import com.example.spring.boardservice.domain.entity.Board;
import com.example.spring.boardservice.dto.BoardListItemResponseDto;
import com.example.spring.boardservice.dto.BoardSearchRequestDto;
import com.example.spring.boardservice.dto.BoardWithCommentsResponseDto;
import com.example.spring.boardservice.mapper.BoardMapper;
import com.example.spring.boardservice.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardApiController {

    private final BoardService boardService;
    private final BoardMapper boardMapper;

    @GetMapping("/search")
    public Page<BoardListItemResponseDto> searchBoards(
            @ModelAttribute BoardSearchRequestDto dto,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        return boardService.searchBoards(dto, PageRequest.of(page - 1, size));
    }

    @GetMapping("/{id}/with-comments")
    public BoardWithCommentsResponseDto getBoardComments(@PathVariable("id") long id) {
        Board board = boardService.getBoardWithComments(id);
        return boardMapper.toBoardWithCommentsResponseDto(board);
    }

}
