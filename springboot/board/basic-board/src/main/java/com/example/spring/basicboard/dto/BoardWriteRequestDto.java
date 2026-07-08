package com.example.spring.basicboard.dto;

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
    private String title;
    private String content;
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
    private MultipartFile file;
}
