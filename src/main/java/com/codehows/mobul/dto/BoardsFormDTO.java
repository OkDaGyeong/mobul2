package com.codehows.mobul.dto;

import com.codehows.mobul.entity.Boards;
import com.codehows.mobul.entity.Users;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class BoardsFormDTO {

    private Long boardId;               // 게시판 번호

    @NotBlank
    private String boardTitle;          //게시판 제목

    @NotBlank
    private String boardContent;        // 게시판 내용

    private int boardView; //조회수--추가

    private int boardLike;

    private Users boardWriter ;         // 게시판 작성자--추가


    private String boardTag;            // 게시판 해시태그

    private LocalDateTime boardDate;    // 작성한 시간

    private List<BoardsFileDTO> boardsFileDTOList = new ArrayList<>();   // 이미지 수정 시 이미지 정보 저장 리스트

    private List<Long> fileId = new ArrayList<>();      // 파일 고유 값 저장


    private static ModelMapper modelMapper = new ModelMapper();

    public Boards createBoard(){
        return modelMapper.map(this, Boards.class);
    }

    public static BoardsFormDTO of(Boards boards){
        return modelMapper.map(boards, BoardsFormDTO.class);
    }

    public String getBoardWriter(){
        return this.boardWriter.getUserId();
    }
}
