package com.codehows.mobul.entity;

import com.codehows.mobul.dto.CommentsDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column
    private String commentWriter;

    @Column
    private String commentContents;

    // 보드 : 코멘트   1:N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="board_id")
    private Boards boards;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp")
    private LocalDateTime commentDateTime;




    public static Comments toSaveEntity(CommentsDTO commentsDTO, Boards boards) {
        Comments comments = new Comments();
        comments.setCommentWriter(commentsDTO.getCommentWriter());  // 게시글 작성자
        comments.setCommentContents(commentsDTO.getCommentContents());
        comments.setBoards(boards);   // 부모 객체 외래키 불러오기 위한 용도  // 게시판 번호도 넣기 위해
        comments.setCommentDateTime(commentsDTO.getCommentDateTime());
        return comments;

    }
}
