package com.example.spring.boardservice.domain.repository;

import com.example.spring.boardservice.domain.entity.Board;
import com.example.spring.boardservice.dto.BoardAuthorStatsResponseDto;
import com.example.spring.boardservice.dto.BoardListItemResponseDto;
import com.example.spring.boardservice.dto.BoardSearchRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BoardRepositoryCustom {

    Page<BoardListItemResponseDto> searchBoards(BoardSearchRequestDto condition, Pageable pageable);

    Optional<Board> findWithComments(Long id);

    List<BoardAuthorStatsResponseDto> countBoardsByAuthor(long minCount);
}
