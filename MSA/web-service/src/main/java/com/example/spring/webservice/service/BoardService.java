package com.example.spring.webservice.service;

import com.example.spring.webservice.client.BoardClient;
import com.example.spring.webservice.dto.BoardPageResponseDto;
import com.example.spring.webservice.dto.BoardSearchRequestDto;
import com.example.spring.webservice.dto.BoardWithCommentsResponseDto;
import com.example.spring.webservice.dto.BoardWriteRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public void saveBoard( String authorization, BoardWriteRequestDto dto ) {
        boardClient.saveBoard(
                authorization,
                dto.getTitle(),
                dto.getContent(),
                dto.getUserId(),
                emptyToNull(dto.getFile())
        );
    }

    private MultipartFile emptyToNull(MultipartFile file) {
        return (file == null || file.isEmpty()) ? null : file;
    }

}
