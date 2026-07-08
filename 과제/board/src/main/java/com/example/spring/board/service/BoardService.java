package com.example.spring.board.service;

import com.example.spring.board.domain.entity.Board;
import com.example.spring.board.domain.repository.BoardRepository;
import com.example.spring.board.dto.BoardDeleteRequestDto;
import com.example.spring.board.dto.BoardUpdateRequestDto;
import com.example.spring.board.exception.BoardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public List<Board> getBoardList(int page, int size) {
        PageRequest pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());

        return boardRepository.findAll(pageable).getContent();
    }

    public int getTotalBoards() {
        return (int) boardRepository.count();
    }

    public Board getBoardDetail(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("게시글을 찾을 수 없습니다. id: " + id));
    }

    @Transactional
    public void saveArticle(String userId, String title, String content, MultipartFile file) {
        String filePath = storeFile(file);

        Board board = Board.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .filePath(filePath)
                .created(LocalDateTime.now())
                .build();
        boardRepository.save(board);
    }

    public Resource downloadFile(String fileName) {
        try {
            File file = new File(new File(uploadDir).getAbsoluteFile(), fileName);
            Resource resource = new UrlResource(file.toURI());
            if (!resource.exists() || !resource.isReadable()) {
                throw new BoardNotFoundException("파일을 찾을 수 없습니다. 파일명: " + fileName);
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new IllegalStateException("파일 경로가 잘못되었습니다.", e);
        }
    }

    @Transactional
    public void updateAriticle(Long id, BoardUpdateRequestDto request) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("게시글을 찾을 수 없습니다. id: " + id));

        String filePath = board.getFilePath();
        if (request.isFileFlag()) {
            if (filePath != null && !filePath.isBlank()) deleteFile(board.getFilePath());
            filePath = storeFile(request.getFile());
        }
        board.update(request.getTitle(), request.getContent(), filePath);
    }

    @Transactional
    public void deleteArticle(Long id, BoardDeleteRequestDto request) {
        if (!boardRepository.existsById(id)) {
            throw new BoardNotFoundException("게시글을 찾을 수 없습니다. id: " + id);
        }
        boardRepository.deleteById(id);
        deleteFile(request.getFilePath());
    }

    private void deleteFile(String filePath) {
        if (filePath != null || filePath.isBlank()) return;
        File file = new File(filePath);
        if (!file.exists()) file.delete();
    }

    private String storeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;

        try {
            File dir = new File(uploadDir).getAbsoluteFile();
            if (!dir.exists()) dir.mkdirs();

            String storeName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(dir, storeName);

            file.transferTo(dest);
            return dest.getPath();
        } catch (IOException e) {
            throw new IllegalStateException("파일 저장에 실패했습니다.", e);
        }
    }
}
