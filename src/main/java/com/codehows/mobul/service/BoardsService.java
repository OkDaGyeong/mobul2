package com.codehows.mobul.service;


import com.codehows.mobul.dto.BoardsFileDTO;
import com.codehows.mobul.dto.BoardsFormDTO;
import com.codehows.mobul.entity.Boards;
import com.codehows.mobul.entity.BoardsFile;
import com.codehows.mobul.entity.Users;
import com.codehows.mobul.repository.AuthRepository;
import com.codehows.mobul.repository.BoardsFileRepository;
import com.codehows.mobul.repository.BoardsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardsService {


    private final BoardsRepository boardsRepository;

    private final BoardsFileService boardsFileService;

    private final BoardsFileRepository boardsFileRepository;

    @Autowired
    private AuthRepository authRepository;



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
    //태그로 검색
    public Page<Boards> boardSearchList3(String searchHashtag,Pageable pageable){
        return boardsRepository.findByBoardTagContaining(searchHashtag,pageable);
    }

    //작성자 불러오기
    public String getBoardWriter(Long boardId){
        Boards boards = boardsRepository.findByBoardId(boardId);
        if(boards == null){
            return null;
        }
        Optional<Users> usersOptional = authRepository.findByUserId(String.valueOf(boards.getBoardWriter()));
        if(usersOptional.isEmpty()){
            return null;
        }
        return usersOptional.get().getUserId();

    }


    // 수정페이지 : 개별 게시글 불러오기
    @Transactional(readOnly = true)
    public BoardsFormDTO getBoardDtl(Long boardId){ //-257
        Boards boards = boardsRepository.findByBoardId(boardId);//.orElseThrow(EntityNotFoundException::new);

        List<BoardsFile> boardsFileList = boardsFileRepository.findByFileBoardNumOrderByFileIdAsc(boards);

        List<BoardsFileDTO> boardsFileDTOList = new ArrayList<>();
        for(BoardsFile boardsFile : boardsFileList){

            BoardsFileDTO boardsFileDTO = BoardsFileDTO.of(boardsFile);
            boardsFileDTOList.add(boardsFileDTO);
        }

        BoardsFormDTO boardsFormDTO = BoardsFormDTO.of(boards);
        boardsFormDTO.setBoardsFileDTOList(boardsFileDTOList);

        return boardsFormDTO;
    }

    public Long updateBoard(BoardsFormDTO boardsFormDTO, List<MultipartFile> fileList) throws Exception{

        // 게시글 수정
        System.out.println("태그 테스트: " + boardsFormDTO.getBoardTag());
        Boards boards = boardsRepository.findByBoardId(boardsFormDTO.getBoardId());

        System.out.println("테스트 tag : " + boardsFormDTO.getBoardTag());
        boards.updateBoard(boardsFormDTO);
        List<Long> fileIds =  boardsFormDTO.getFileId();

        // 이미지 등록
        for(int i=0; i<fileList.size();i++){
            boardsFileService.updateFile(fileIds.get(i), fileList.get(i));
        }

        return boards.getBoardId();

    }


    // 게시글 저장 : 본문 + 파일
    public Long saveBoard(BoardsFormDTO boardsFormDTO, List<MultipartFile> fileList, HttpSession session) throws Exception{
        // 세션에서 사용자 정보를 가져옴
        String userId = (String) session.getAttribute("userId");
        Users users = authRepository.findByUserId(userId).orElse(null);

        //게시글 등록
        Boards boards = boardsFormDTO.createBoard();
        boards.setBoardWriter(users); // 로그인한 사용자의 아이디 정보를 게시글 엔티티에 저장
        boardsRepository.save(boards);

        // 이미지 등록
        for(int i=0; i<fileList.size(); i++){
            BoardsFile boardsFile = new BoardsFile();
            boardsFile.setFileBoardNum(boards);
            boardsFileService.saveFile(boardsFile, fileList.get(i));
        }


        return boards.getBoardId();
    }

    public Boards boardview(Long boardId){
        return boardsRepository.getOne(boardId);
    }

    //조회수 올리기
    @Transactional
    public int updateView(Long boardId) {
        return boardsRepository.updateBoardView(boardId);
    }


    // 게시글 ID로 게시글 조회
    public Boards findByBoardId(Long boardId) {
        return boardsRepository.findByBoardId(boardId);
    }
    // 상세페이지
    // 삭제
    public void deleteBoard(Long boardId){
        boardsRepository.deleteByBoardId(boardId);
    }

}
