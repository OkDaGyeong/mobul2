package com.codehows.mobul.dto;

import com.codehows.mobul.entity.Boards;
import com.codehows.mobul.entity.BoardsFile;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class BoardsFileDTO {

    private Long fileId;                // 파일 고유 번호

    private Boards fileBoardNum;        // 게시글 번호

    private String fileName;            // 파일명

    private String fileOriName;         // 원본 파일 파일명

    private String filePath;            // 파일 조회 경로

    private static ModelMapper modelMapper = new ModelMapper();

    public static BoardsFileDTO of(BoardsFile boardsFile) {   // BoardsFile 엔티티 객체 -> DTO 변환
        return modelMapper.map(boardsFile, BoardsFileDTO.class);
    }


    //0-
//    public BoardsFileDTO(Long fileId, Boards fileBoardNum, String fileName, String fileOriName, String filePath){
//        this.fileId = fileId;
//        this.fileBoardNum = fileBoardNum;
//        this.fileName = fileName;
//        this.fileOriName = fileOriName;
//        this.filePath = filePath;
//
//    }
//    public static BoardsFileDTO boardsFileDTO(BoardsFile boardsFile) {   // BoardsFile 엔티티 객체 -> DTO 변환
//        ModelMapper modelMapper = new ModelMapper();
//        return modelMapper.map(boardsFile, BoardsFileDTO.class);
//    }

}
