package com.example.spring.basicboard.service;

import com.example.spring.basicboard.domain.entity.Board;
import com.example.spring.basicboard.domain.repository.BoardRepository;
import com.example.spring.basicboard.dto.BoardDeleteRequestDto;
import com.example.spring.basicboard.exception.BoardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final FileService fileService;

    public List<Board> getBoardList(int page, int size) {
        PageRequest pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());

        // * findAll(pageable).getContent()의 getContent()란?
        // findAll(pageable)의 반환 타입은 Page<Board>다
        // Page가 제공하는 것들
        // - getContent() -> List<Board>: "이번 페이지의 게시글 목록"
        // - getTotalElements() -> long: 전체 게시글 수
        // - getTotalPages() -> int: 전체 페이지 수
        // - isLast() -> boolean: 마지막 페이지 여부
        // 주의: getContent()의 'content'는 Board 엔티티의 content가 아니다.
        return boardRepository.findAll(pageable).getContent();
    }

    public int getTotalBoards() {
        return (int) boardRepository.count();
    }

    @Transactional
    public void saveBoard(String userId, String title, String content, MultipartFile file) {
        String filePath = fileService.storeFile(file);

        Board build = Board.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .filePath(filePath)
                .created(LocalDateTime.now())
                .build();
        boardRepository.save(build);
    }

    public Board getBoardDetail(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("[BOARD] 게시글을 찾을 수 없습니다. id: " + id));
    }

    @Transactional
    public void deleteBoard(Long id, BoardDeleteRequestDto dto) {

        if (!boardRepository.existsById(id)) {
            throw new BoardNotFoundException("[BOARD] 삭제할 게시글을 찾을 수 없습니다. id: " + id);
        }

        boardRepository.deleteById(id);
        fileService.deleteFile(dto.getFilePath());
    }
}
