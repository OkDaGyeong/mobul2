package com.codehows.mobul.entity;

import com.codehows.mobul.dto.BoardsDTO;
import com.codehows.mobul.dto.BoardsFormDTO;
import lombok.*;
import lombok.extern.apachecommons.CommonsLog;
import org.hibernate.annotations.CreationTimestamp;
import org.modelmapper.ModelMapper;

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

    //    게시글 작성자 유저_id랑 외래키 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_board_writer", referencedColumnName="user_id")  // 포린키 설정
    private Users users;

    //게시물 제목 not null    varchar(50)
    @Column
    private String boardTitle;

    // 게시물 본문 not null   text
    @Column(nullable = false, columnDefinition = "TEXT")
    private String boardContent;

    //게시글 조회수 int defalt 0
    @Column(columnDefinition = "integer default 0")
    private int boardView;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp")
    private LocalDateTime boardDate;


    //게시글 좋아요   default 0  : 좋아요 낫널 확인필요
    @Column(columnDefinition = "integer default 0")
    private int boardLike;

//    //게시물 해시태그  varchar(30)
//    @Column(length = 30)
//    private String boardTag;

    // 게시물 작성시간



    // BoardsFormDTO를 엔티티로 변경
    public static Boards toSaveEntity(BoardsDTO boardsDTO,Users users) {
        Boards boards = new Boards();
        boards.setUsers(users);
        boards.setBoardTitle(boardsDTO.getBoardTitle());
        boards.setBoardContent(boardsDTO.getBoardContent());
        boards.setBoardLike(0);
        boards.setBoardView(boardsDTO.getBoardView());
        return boards;
    }

    public void updateBoard(BoardsFormDTO boardsFormDTO) {
    }
}

