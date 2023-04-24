package com.codehows.mobul.dto;

import com.codehows.mobul.entity.Boards;

import com.codehows.mobul.entity.Like;

import com.codehows.mobul.entity.Users;
import lombok.*;
@Getter @Setter @ToString
@NoArgsConstructor      //기본 생성자
@AllArgsConstructor     // 모든 필드를 매개변수로 하는 생성자     들을 자동으로 만들어줌
public class LikeDTO {
    private Long likeId; //고유번호
    private Users likeUserId; //
    private Boards likeBoardId;

    /* DTO -> Entity */

    /* Entity -> DTO */ //작성 아직 안함
}