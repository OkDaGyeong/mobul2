package com.codehows.mobul.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter @Setter @ToString
@NoArgsConstructor      //기본 생성자 자동 생성
@AllArgsConstructor     // 모든 필드를 매개변수로 하는 생성자 들을 자동으로 만들어줌
public class BoardsDTO {

    private int boardId;        // 게시판 번호 

    private String boardTitle;     //게시판 제목

    private String boardContent;     // 게시판 내용

    private int boardView;          // 게시판 조회수

    private String boardWriter;     // 게시판 작성자 

    private int boardLike;          // 게시판 좋아요 수 

    private String boardTag;        // 게시판 해시태그

    private LocalDateTime boardDate;       // 작성한 시간


}
