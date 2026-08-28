package com.example.spring.boardservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length = 50)
    private String userId;

    @Column(length = 255)
    private String filePath;

    // org : 2026-01-01T00:00:00
    // -> 2026-01-01 00:00
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime created;

    // 역방향 연관관계 :  "한 게시글(One)이 여러 댓글(Many)을 가진다." (1:N)
    // * mappedBy = "board"
    // - 이 관계의 "주인"은 Comment.board(FK를 가진 쪽)이고, 여기 Board.comments는 "읽기용"이다.
    // - mappedBy는 "주인이 누군인지"를 알려준다. -> "Comment의 board필드가 이 관계의 주인이다"라는 뜻
    // * 이 필드를 왜 두나? -> fetch join
    // - 이게 있어야 "게시글 하나 + 그 댓글들"을 한 번의 fetch join으로 가져오는 쿼리를 만들 수 있다.
    // - 반대로 이게 없으면 board.getComments()로 댓글을 순회할 수 없다.
    // * @Builder.Default를 붙이는 이유
    // - @Builder는 빌더로 객체를 만들 때 필드 초기화식(= new ArrayList<>())을 무시한다.
    //   빌더는 내부적으로 "빌더에 설정된 값"만으로 생성자를 호출하기 때문에,
    //   comments를 지정하지 않고 Board.builder().build()로 만들면 comments가 null이 된다.
    // - 그러면 board.getComments()를 순회하는 순간 NullPointerException이 터질 수 있다.
    // - @Builder.Default를 붙이면 "빌더에서 값을 안 넣었을 때 이 초기화식을 기본값으로 써라"는
    //   뜻이 되어, 빌더로 생성해도 빈 리스트로 안전하게 초기화된다.
    @OneToMany(mappedBy = "board")
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    // 게시글 수정
    public void update(String title, String content, String filePath) {
        this.title = title;
        this.content = content;
        this.filePath = filePath;
    }

}
