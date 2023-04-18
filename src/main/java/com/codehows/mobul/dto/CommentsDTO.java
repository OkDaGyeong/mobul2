
package com.codehows.mobul.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentsDTO {

    // 댓글 번호
    private Long commentId;

    // 댓글 내용
    private String commentContent;

    // 댓글 작성자 ID
    private Long commentWriterId;

    // 댓글 작성시간
    private LocalDateTime commentDate;

    // 댓글이 속한 게시물 번호
    private Long boardId;
}

