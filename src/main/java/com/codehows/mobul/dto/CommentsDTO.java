package com.codehows.mobul.dto;


import com.codehows.mobul.entity.Comments;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter @ToString
public class CommentsDTO {
    private Long commentId; // id -> commentId
    private String commentWriter;       // 작성자
    private String commentContents;      // 내요
    private Long boardId;           // 보드랑  외래키 설정
    private LocalDateTime commentDateTime;      // 시간설ㅈ엉



    // DTO ----> Entity
    public static CommentsDTO toCommentsDTO(Comments comments, Long boardId) {
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setCommentId(comments.getCommentId());
        commentsDTO.setCommentWriter(comments.getCommentWriter());
        commentsDTO.setCommentContents(comments.getCommentContents());
        commentsDTO.setCommentDateTime(comments.getCommentDateTime());
//        commentsDTO.setBoardId(comments.getBoards().getBoardId()); // Service 메서드에 @Transactional
        commentsDTO.setBoardId(boardId);    // 이 문법이 가독성이 좋다
        return  commentsDTO;
    }
}
