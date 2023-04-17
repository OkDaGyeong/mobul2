package com.codehows.mobul.service;


import com.codehows.mobul.dto.BoardsDTO;
import com.codehows.mobul.dto.BoardsFileDTO;

import com.codehows.mobul.dto.BoardsFormDTO;
import com.codehows.mobul.entity.Boards;
import com.codehows.mobul.entity.BoardsFile;
import com.codehows.mobul.repository.BoardsFileRepository;
import com.codehows.mobul.repository.BoardsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//import org.springframework.web.multipart.MultipartFile;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
//import java.io.File;
import java.io.File;
import java.util.ArrayList;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardsService {
    @Autowired
    private BoardsRepository boardsRepository;
    @Autowired
    private final BoardsFileService boardsFileService;

    @Autowired
    private final BoardsFileRepository boardsFileRepository;

    @Autowired
    private final BoardsFileService boardsFileService;

    @Autowired
    private final BoardsFileRepository boardsFileRepository;

//    @Autowired
//    private UserRepository userRepository;    //

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



    //개별 게시글 불러오기
//    @Transactional(readOnly = true)
    public BoardsFormDTO getBoardDtl(Long boardId){ //-257
        List<BoardsFile> boardsFileList = boardsFileRepository.findByFileBoardNumOrderByFileIdAsc(boardId);
        List<BoardsFileDTO> boardsFileDTOList = new ArrayList<>();
        for(BoardsFile boardsFile : boardsFileList){
            BoardsFileDTO boardsFileDTO = BoardsFileDTO.of(boardsFile);
            boardsFileDTOList.add(boardsFileDTO);
        }

        Boards boards = boardsRepository.findById(boardId)
                .orElseThrow(EntityNotFoundException::new);
        BoardsFormDTO boardsFormDTO = BoardsFormDTO.of(boards);
        boardsFormDTO.setBoardsFileDTOList(boardsFileDTOList);
        return boardsFormDTO;
    }


    public Long saveBoard(BoardsFormDTO boardsFormDTO, List<MultipartFile> fileList) throws Exception{
        // 게시글 등록
//        Users users = usersRepository.findById(boardsDTO.getUsersId()).orElse(null);

        Boards boards = boardsFormDTO.createBoard();
        boardsRepository.save(boards);

        // 이미지 등록
        for(int i=0; i<fileList.size(); i++){
            BoardsFile boardsFile = new BoardsFile();
            boardsFile.setFileBoardNum(boards);
            boardsFileService.saveFile(boardsFile, fileList.get(i));
        }

        return boards.getBoardId();
    }

}
