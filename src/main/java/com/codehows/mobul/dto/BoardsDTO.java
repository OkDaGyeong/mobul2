package com.codehows.mobul.dto;

import com.codehows.mobul.entity.Boards;
import com.codehows.mobul.entity.Users;
import lombok.*;

import java.time.LocalDateTime;


@Getter @Setter @ToString
@NoArgsConstructor      //기본 생성자
@AllArgsConstructor     // 모든 필드를 매개변수로 하는 생성자     들을 자동으로 만들어줌
public class BoardsDTO {

    private Long boardId;               // 게시판 번호

    private String boardTitle;          //게시판 제목

    private String boardContent;        // 게시판 내용

    private Integer boardView;             // 게시판 조회수

    private Users boardWriter ;         // 게시판 작성자

    private Integer boardLike;             // 게시판 좋아요 수

    private String boardTag;            // 게시판 해시태그

    private LocalDateTime boardDate;    // 작성한 시간

    public  BoardsDTO (Long boardId, String boardContent, String boardTitle, int boardLike, LocalDateTime boardDate){
        this.boardId = boardId;
        this.boardContent = boardContent;
        this.boardTitle = boardTitle;
        this.boardLike = boardLike;
        this.boardDate = boardDate;
    }

    public static BoardsDTO toBoardDTO(Boards boards, Users users){
        BoardsDTO boardsDTO = new BoardsDTO();
        boardsDTO.setBoardId(boards.getBoardId());
        boardsDTO.setBoardWriter(users);
        boardsDTO.setBoardTitle(boards.getBoardTitle());
        boardsDTO.setBoardContent(boards.getBoardContent());
        boardsDTO.setBoardView(boards.getBoardView());
        boardsDTO.setBoardDate(boards.getBoardDate());
        return boardsDTO;
    }
 }
