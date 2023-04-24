package com.codehows.mobul.controller;

import com.codehows.mobul.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
@RequestMapping("like")
public class LikeController {
    private final LikeService likeService;

    //좋아요 누르기
    @PostMapping("/{boardId}")
    public ResponseEntity likeSave(@PathVariable Long boardId, HttpSession session) {


        return ResponseEntity.ok(likeService.likeSave(session.getAttribute("userId").toString(),boardId));
    }

    /* DELETE - 좋아요 취소 */

    @DeleteMapping("/{boardId}")
    public ResponseEntity deleteLike( @PathVariable Long boardId,  HttpSession session) {
        return ResponseEntity.ok(likeService.deleteLike(session.getAttribute("userId").toString(), boardId));
    }

}
