package com.codehows.mobul.service;


import com.codehows.mobul.entity.Boards;
import com.codehows.mobul.repository.BoardsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
//import java.io.File;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardsService {
    @Autowired
    private BoardsRepository boardsRepository;

    // 파일 입력
    public void write(Boards boards) {boardsRepository.save(boards);}


    //전체 목록 불러오기
    public List<Boards> findAllBoards() {
        return boardsRepository.findAll();
    }
    //페이징 처리를 위함
    public Page<Boards> boardList(Pageable pageable){
        //기존 List<Board>값으로 넘어가지만 페이징 설정을 해주면 Page<Board>로 넘어갑니다.
        return boardsRepository.findAll(pageable);
    }

    //제목으로 검색
    public Page<Boards> boardSearchList(String searchTitle,Pageable pageable){
        return boardsRepository.findByBoardTitleContaining(searchTitle,pageable);
    }
    //본문으로 검색
    public Page<Boards> boardSearchList2(String searchContent,Pageable pageable){
        return boardsRepository.findByBoardContentContaining(searchContent,pageable);
    }





    // 파일 입력 -- 수정 필요
//    public void write(Boards boards, MultipartFile file) throws Exception{
//        // 파일 저장 경로
//        String projectPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files";
//        // 랜덤으로 파일 이름 만들기
//        UUID uuid = UUID.randomUUID();
//        String fileName = uuid + "-" + file.getOriginalFilename();
//
//        // 파일 넣을 객체
//        File saveFile = new File(projectPath, fileName);
//        file.transferTo(saveFile);
//
//        boardsRepository.save(boards);
//    }




//    private  final BoardsRepository boardsRepository;


//    public Long saveBoards(BoardsFormDTO boardsFormDTO) throws Exception{
//        // 게시글 등록
//        Boards boards = boardsFormDTO.createBoards();
//        boardsRepository.save(boards);
//
//        return boards.getBoardId();
//    }

}
