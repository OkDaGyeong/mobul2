package com.codehows.mobul.dto;

import com.codehows.mobul.entity.Boards;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardsFileFormDTO {

    private Long fileId;            // 게시글 번호

    private Boards fileBoardNum;    // 게시글 번호

    private String fileName;        // 저장 파일명

    private String fileOriName;     // 실제 파일명

    private String filePath;        // 파일 조회 경로


}
