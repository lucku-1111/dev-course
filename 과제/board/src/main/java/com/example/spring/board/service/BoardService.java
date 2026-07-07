package com.example.spring.board.service;

import com.example.spring.board.domain.entity.Board;
import com.example.spring.board.domain.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public List<Board> getBoardList(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return boardRepository.findAll(pageable).getContent();
    }

    @Transactional(readOnly = true)
    public int getTotalBoards() {
        return (int) boardRepository.count();
    }
}
