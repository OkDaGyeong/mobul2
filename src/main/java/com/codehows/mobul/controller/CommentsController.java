package com.codehows.mobul.controller;


import com.codehows.mobul.dto.CommentsDTO;
import com.codehows.mobul.repository.BoardsFileRepository;
import com.codehows.mobul.repository.BoardsRepository;
import com.codehows.mobul.repository.CommentsRepository;
import com.codehows.mobul.service.AuthService;
import com.codehows.mobul.service.BoardsService;
import com.codehows.mobul.service.CommentsService;
import com.codehows.mobul.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private final CommentsRepository commentsRepository;

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

    // 댓글삭제
    @DeleteMapping(value = "/comment/delete/{commentId}")
    public @ResponseBody ResponseEntity deleteComment(@PathVariable("commentId") Long commentId) {
        commentsRepository.deleteById(commentId);
        return ResponseEntity.ok(commentId);
    }
}
