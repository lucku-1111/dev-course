package com.example.spring.basicboard.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

// * @ModelAttribute
// 폼 필드가 같은 이름의 필드에 자동으로 채워진다.
// - 파일은 JSON에 못 실으므로 multipart + @ModelAttribute 로 쓴다.
// 그래서 아래 필드 이름은 화면 폼의 name 속성과 "똑같아야" 한다.
// @Setter / @NoArgsConstructor
// - @ModelAttribute는 "기본 생성자로 객체를 만든 뒤 setter로 값을 하나씩 채우는" 방식이다.
// - 그래서 응답 DTO들처럼 @Builder만 있으면 안되고, @Setter / @NoArgsConstructor 가 있어야 한다.
@Getter
@Setter
@NoArgsConstructor
public class BoardWriteRequestDto {

    @Schema(description = "게시글 제목", example = "첫 글입니다.")
    private String title;
    @Schema(description = "게시글 내용", example = "안녕하세요. 반갑습니다.")
    private String content;
    @Schema(description = "작성자 아이디(로그인 사용자)", example = "user01")
    private String userId;
    // MultipartFile
    // multipart/form-data 로 업로드된 "파일 한 개"를 스프링이 감싸서 넘겨주는 타입이다.
    // 파일의 바이트뿐 아니라 메타데이터도 함께 들고 있다. 자주 쓰는 메서드:
    //     getOriginalFilename() : 업로드된 원본 파일명 (예: "고양이.png")
    //     getContentType()      : MIME 타입 (예: "image/png")
    //     getSize()             : 파일 크기(byte)
    //     isEmpty()             : 파일을 안 골랐거나 빈 파일이면 true
    //     getInputStream()      : 내용을 읽는 스트림
    //     transferTo(dest)      : 실제 디스크 경로로 저장
    // * @Schema: 파일필드는 반드시 @Schema(type = "string", format = "binary")로 알려줘야 한다.
    // - 이게 있어야 Swagger UI가 이 칸을 "파일 선택" 버튼으로 그린다.(없으면 그냥 텍스트..)
    // - 컨트롤러의 consumes=multipart/form-data 설정과 짝을 이룬다.
    @Schema(description = "첨부파일(선택). 안고르면 비어있음", type = "string", format = "binary")
    private MultipartFile file;
}
