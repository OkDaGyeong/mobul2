package com.codehows.mobul.entity;

import com.codehows.mobul.dto.CommentsDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@ToString
public class Comments {

    // 댓글 번호 pk not null  auto_increment
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    // 댓글 내용 not null text
    @Column(nullable = false, columnDefinition = "TEXT")
    private String commentContent;

    // 댓글 작성자 not null fk 설정   users 테이블의 user_id와 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_writer", referencedColumnName="user_id")  // 포린키 설정
    private Users commentWriter;

    // 댓글 작성시간
    @CreationTimestamp
    @Column(columnDefinition = "timestamp")
    private LocalDateTime commentDate;

    // 댓글이 속한 게시물
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Boards boards;

    // CommentsFormDTO를 엔티티로 변경
    public static Comments toComments(CommentsDTO commentsDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(commentsDTO, Comments.class);
    }

    public void updateComment(CommentsDTO commentsDTO){
        this.commentId = commentsDTO.getCommentId();
        this.commentContent = commentsDTO.getCommentContent();
//        this.commentWriter = commentsDTO.getCommentWriter();
        this.commentDate = commentsDTO.getCommentDate();
    }
}
