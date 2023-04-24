package com.codehows.mobul.controller;


import com.codehows.mobul.dto.BoardsFormDTO;
import com.codehows.mobul.dto.CommentsDTO;
import com.codehows.mobul.entity.Boards;
import com.codehows.mobul.entity.BoardsFile;
import com.codehows.mobul.entity.Users;
import com.codehows.mobul.repository.BoardsFileRepository;
import com.codehows.mobul.repository.BoardsRepository;
import com.codehows.mobul.service.AuthService;
import com.codehows.mobul.service.BoardsService;
import com.codehows.mobul.service.CommentsService;
import com.codehows.mobul.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class CommentsController {


    private final BoardsService boardsService;

    private final LikeService likeService;

    private final AuthService authService;

    private final CommentsService commentsService;
    private final BoardsFileRepository boardsFileRepository;
    private final BoardsRepository boardsRepository;


    //----상세페이지
    @GetMapping(value="/comment/{boardId}")
    public String boardDtl(Model model, @PathVariable("boardId") Long boardId , HttpSession session ){
        boardsService.updateView(boardId); //조회수 증가
        Boards boards = boardsService.findByBoardId(boardId);

        // 댓글 목록 가져오기
        List<CommentsDTO> commentsDTOList = commentsService.findAll(boardId);
        model.addAttribute("commentsList", commentsDTOList);   //  html에서 사용할 수 있게 값들을 넘겨 준다
        System.out.println("commentsDTOList = " + commentsDTOList);
        BoardsFormDTO boardsFormDTO = boardsService.getBoardDtl(boardId);

        model.addAttribute("boardsForm", boardsFormDTO);

        //--like관련
        Long likeCount = likeService.findLikeCount(boards);
        model.addAttribute("likeCount", likeCount);

        // 로그인 유저 아이디 확인,
        System.out.println("로그인된 유저 id : "+session.getAttribute("userId"));
        String loginUserId = (String)session.getAttribute("userId");

        Optional<Users> users = authService.findByUserId(loginUserId);

        if(loginUserId != null){ // 로그인 했는지 구분
            if(likeService.findLike(users, boards)){ // 게시글에 좋아요를 누른 사람인지 확인
                model.addAttribute("isLiked",true);
            }else {
                model.addAttribute("isLiked", false);
            }
        }else {
            model.addAttribute("isLiked", false);
        }
        return "/boards/comment";
    }
    
/*    @PostMapping("/comment/save")
    public ResponseEntity  save(@ModelAttribute CommentsDTO commentsDTO){
        System.out.println("commentsDTO = " + commentsDTO);
        Long saveResult = commentsService.save(commentsDTO);
        if(saveResult != null){   // 댓글을 작성성공 하면 기존 댓글에 기존 댓글이 추가된 목록을 다시 보여 줘야 된다
            //작성 성공하면 댓글목록을 가져와서 리턴 ( 반복분 )

            //댓글 목록이란  : 해당 게시글의 댓글 전체  (그러므로 해당 게시물의 아이디 기준으로 모두 가져와야 한다)
            List<CommentsDTO> commentsDTOList = commentsService.findAll(commentsDTO.getBoardId());
            return new ResponseEntity<>(commentsDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 게시글이 존재 x ", HttpStatus.NOT_FOUND);
        }


    }
        */



    @PostMapping("/comment/save")
    public ResponseEntity save(@ModelAttribute CommentsDTO commentsDTO){

        Long saveResult = commentsService.save(commentsDTO);
        System.out.println("컨트롤세이브 컨트롤러 코멘트디티오 = " + commentsDTO);
        if (saveResult != null){
            //작성 성공
            //댓글을 작성하면 기존 댓글에 댓글이 추가된 목록을 보여줘야 한다.
            // 댓글 성공하면 댓글 목록을 가져와 리턴 ( 댓글 목록이라 하면 상세 페이지의 모든 댓글 )
           List<CommentsDTO> commentsDTOList = commentsService.findAll(commentsDTO.getBoardId());
           return new ResponseEntity<>(commentsDTOList, HttpStatus.OK);

        } else{
            return new ResponseEntity<>("해당 게시글 존재 안합니다", HttpStatus.NOT_FOUND);
        }

    }



    // 게시글 삭제
    @GetMapping("/comment/delete/{boardId}")
    public String deleteBoard(@PathVariable Long boardId){

        // id 조회
        Boards board = boardsRepository.findByBoardId(boardId);
        //                .orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + boardId));

        // file 객체 삭제
        List<BoardsFile> files = boardsFileRepository.findByFileBoardNumOrderByFileIdAsc(board);   //
        if(!files.isEmpty()){
            boardsFileRepository.deleteAll(files);
        }

        // Boards 객체 삭제
        boardsRepository.delete(board);

        return "redirect:/";
    }
}
