package com.codehows.mobul.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter @Setter
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id")  // 포린키 설정
    private Users likeUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Boards likeBoardId;

    //일단 생성자를 public으로전환
    public Like(){
        Long likeId = this.likeId;
        Users likeUserId = this.likeUserId;
        Boards likeBoardId = this.likeBoardId;
    }
}