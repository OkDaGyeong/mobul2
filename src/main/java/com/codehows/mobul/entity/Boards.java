package com.codehows.mobul.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "boards")
@Getter @Setter @ToString
public class Boards {


    //게시물 번호 pk not null  auto_increment
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    //게시물 제목 not null    varchar(50)
    @Column(nullable = false, length = 50)
    private String boardTitle;

    // 게시물 본문 not null   text
    @Column(nullable = false, columnDefinition = "TEXT")
    private String boardContent;

    //게시글 조회수 int defalt 0
    @Column(columnDefinition = "integer default 0")
    private Long boardView;

    //게시글 작성자 not null fk 설정   users 테이블의 user_id와 연결
    @Column(nullable = false, length = 20)
    private String boardWriter;

    //게시글 좋아요   default 0  : 좋아요 낫널 확인필요
    @Column(columnDefinition = "integer default 0")
    private Long boardLike;

    //게시물 해시태그  varchar(30)
    @Column(length = 30)
    private String boardTag;

    // 게시물 작성시간
    @CreationTimestamp
    @Column(columnDefinition = "timestamp")
    private LocalDateTime boardDate;

    // 포린키를 생성시는 꼭 클래스의 객체를 만들어 줘야 한다
    // users 객체가 user_id 칼럼이 되고
    // users 의 user_id  동작이 같이 ...
   @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // 포린키 설정
    private Users users;



//    // 파일 업로드 -- 수정중
//    private String fileName;
//
//    private String filePath;

}

