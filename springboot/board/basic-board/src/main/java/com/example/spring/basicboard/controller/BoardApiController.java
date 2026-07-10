package com.example.spring.basicboard.controller;

import com.example.spring.basicboard.domain.entity.Board;
import com.example.spring.basicboard.dto.*;
import com.example.spring.basicboard.service.BoardService;
import com.example.spring.basicboard.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

// * Swagger 어노테이션들
// - @Tag               : 컨트롤러(그룹) 단위로 설명 - 화면에서 API를 묶는 큰 제목이 된다.
// - @Operation         : 메서드(API 한 개) 단위의 설명 - 요약(summary)/상세(description)
// - @Parameter         : 파라미터 하나에 대한 설명
// - @ApiResponse(s)    : 이 API가 낼 수 있는 응답(상태코드별)을 문서에 명시
// - @Content / @Schema : 응답/요청 본문의 "형태(어떤 DTO인지)"를 지정

@Tag(name = "게시글 API", description = "게시글 목록/상세 조회, 작성, 수정, 삭제, 첨부파일 다운로드")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardApiController {

    private final BoardService boardService;
    private final FileService fileService;

    @Operation(
            summary = "게시글 목록 조회",
            description = "페이지 단위로 게시글 목록을 조회한다. 목록(boards)과 마지막 페이지 여부(last), 전체 페이지 수(totalPages)를 함께 돌려준다."
    )
    @GetMapping
    public BoardListResponseDto getBoardList(
            @Parameter(description = "조회할 페이지 번호 (1부터 시작)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "한 페이지에 담을 게시글 수", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        // 게시글 목록
        List<Board> boards = boardService.getBoardList(page, size);

        // 전체 게시글 수 가져오기
        int totalBoards = boardService.getTotalBoards();

        // 전체 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalBoards / size);

        boolean last = page >= totalPages;

        return BoardListResponseDto.builder()
                .boards(boards)
                .last(last)
                .totalPages(totalPages)
                .build();
    }

    // * Swagger 에서 "파일 업로드(multipart)" 를 제대로 그리게 하는 핵심
    // # 문제: @ModelAttribute + MultipartFile 을 그냥 두면, Swagger 가 이걸 JSON 본문으로 오해하거나
    //         파일 선택 버튼을 안 그려서 UI 에서 테스트가 안 된다
    // # 해결 2가지 (둘을 같이 써야 완성된다):
    //   (1) 여기 @PostMapping 에 consumes = MULTIPART_FORM_DATA_VALUE 를 "명시" 한다
    //       -> springdoc 이 "아, 이 API 는 JSON 이 아니라 multipart 폼이구나" 를 알고 폼 형태로 그린다
    //       -> 덤으로 이 엔드포인트가 multipart 요청만 받도록 더 엄격/정확해진다 (JS 는 원래 multipart 로 보냄)
    //   (2) DTO 의 MultipartFile 필드에 @Schema(type="string", format="binary") 를 붙인다
    //       -> 그래야 그 칸이 "파일 선택" 버튼으로 렌더링된다 (BoardWriteRequestDto 참고)
    @Operation(summary = "게시글 작성",
            description = "제목/내용/작성자와 (선택적) 첨부파일을 multipart/form-data 로 받아 새 게시글을 저장한다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveBoard(@ModelAttribute BoardWriteRequestDto dto) {
        boardService.saveBoard(dto.getUserId(), dto.getTitle(), dto.getContent(), dto.getFile());
    }

    // @ApiResponses = "이 API 가 낼 수 있는 응답들" 을 상태코드별로 문서에 나열한다
    //   - 성공(200)만이 아니라 실패(404)도 미리 적어두면, 이 API 를 쓰는 사람이 어떤 상황을 대비해야 하는지 한눈에 안다
    //   - 404 의 응답 본문 형태(schema)를 ErrorResponseDto 로 지정하면, 실패 시 무엇이 오는지까지 문서에 드러난다
    @Operation(summary = "게시글 상세 조회", description = "id로 게시글 한 건의 상세 내용을 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 상세 조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글 상세 조회 실패 - 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}")
    public BoardDetailResponseDto getBoardDetail(
            @Parameter(description = "조회할 게시글 id", example = "1")
            @PathVariable Long id
    ) {
        Board board = boardService.getBoardDetail(id);
        return BoardDetailResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .filePath(board.getFilePath())
                .created(board.getCreated())
                .userId(board.getUserId())
                .build();
    }

    // ResponseEntity는 HTTP응답의 3가지를 직접 제어하게 해주는 상자(Wrapper)다.
    // [상태코드] + [헤더] + [본문(body)]
    // 그냥 Resource만 리턴하면 파일 내용은 내려가지만,
    // Content-Disposition: attachment 헤더를 붙일 방법이 없다.
    // -> 그러면 다운로드가 아니라 브라우저가 파일을 그냥 열어버리고, 저장 파일명도 못 정한다.
    // 응답이 "파일(바이너리)" 임을 문서에 알려주기
    //   - 이 API 는 JSON 이 아니라 파일 그 자체를 내려준다
    //   - 응답 형태를 octet-stream + Schema(format="binary") 로 지정하면, 문서에 "다운로드되는 바이너리" 로 표시된다
    @Operation(summary = "첨부파일 다운로드",
            description = "저장된 파일 이름으로 첨부파일을 내려받는다. Content-Disposition: attachment 로 브라우저가 다운로드하게 한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "파일 다운로드",
                    content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                            schema = @Schema(type = "string", format = "binary"))),
            @ApiResponse(responseCode = "404", description = "해당 이름의 파일이 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/file/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(
            @Parameter(description = "서버에 저장된 파일 이름(UUID 포함)", example = "3f2a1b_이력서.pdf")
            @PathVariable String fileName
    ) {
        Resource resource = fileService.downloadFile(fileName);

        // * 한글 파일명 인코딩
        // HTTP 헤더 값에는 원칙적으로 ASCII만 안전하게 담을 수 있다.
        // -> "이력서.pdf"같은 한글/공백을 그대로 넣으면 깨지거나 잘린다.
        // 그래서 파일명을 URL 인코딩해서 넣는다.
        //  - URLEncoder 는 공백은 '+'로 바꾸는데, 파일명에선 '+'가 그대로 보이면 곤란하므로 %20 으로 치환한다.
        String encodeFileName = URLEncoder.encode(resource.getFilename(), StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");

        // * contentType(MediaType.APPLICATION_OCTET_STREAM) => 힌트
        // 무슨 파일인지 특정하지 않은 순수 바이너리 라는 뜻
        // 브라우저가 열 방법을 몰라 저장 쪽으로 기울게 하는 '힌트'일 뿐, 다운로드 확정하지 못한다.
        // (확자자 등에 따라 브라우저가 그냥 열어버릴 수도 있다.)
        // * .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName)
        // attachment는 인라인으로 열지말고 무조건 첨부(다운로드)하라는 확실한 지시
        // filename*(별표) 는 인코딩을 명시하는 최신 문법으로 "저장될 기본 파일명"을 정한다.
        // 이게 없으면 URL 끝의 UUID 붙음 이름으로 저장돼 버린다. 그래서 원본 이름으로 저장되게 넣는 것
        // utf8 뒤에 '' : 언어필드 생략 (ex utf8'ko')
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodeFileName)
                .body(resource);
    }

    @PutMapping("/{id}")
    public void updateBoard(@PathVariable Long id, @RequestBody BoardUpdateRequestDto dto) {
        boardService.updateBoard(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id, @RequestBody BoardDeleteRequestDto dto) {
        boardService.deleteBoard(id, dto);
    }
}
