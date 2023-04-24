package com.codehows.mobul.service;


import com.codehows.mobul.entity.Boards;
import com.codehows.mobul.entity.Like;
import com.codehows.mobul.entity.Users;
import com.codehows.mobul.repository.AuthRepository;
import com.codehows.mobul.repository.BoardsRepository;
import com.codehows.mobul.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LikeService {

    private final AuthRepository authRepository;
    private final BoardsRepository boardsRepository;
    private final LikeRepository likeRepository;

    //create
    @Transactional
    public Long likeSave(String userId, Long boardId){
        Optional<Users> users = authRepository.findByUserId(userId); //흠..
        Boards boards = boardsRepository.findByBoardId(boardId); //orElsethrow를 못함..


        Like like = new Like();
        like.setLikeUserId(users.get());
        like.setLikeBoardId(boards);
        likeRepository.save(like);

        return like.getLikeId();
    }

    //read
    @Transactional(readOnly = true)
    public boolean findLike(Optional<Users> users, Boards boards) {


        return likeRepository.existsByLikeUserIdAndLikeBoardId(users, boards);
    }

    //read count
    @Transactional(readOnly = true)
    public Long findLikeCount(Boards boards) {
        return likeRepository.countByLikeBoardId(boards);
    }

    /* DELETE */
    @Transactional
    public Long deleteLike(String userId, Long boardId) {
        Optional<Users> users = authRepository.findByUserId(userId);
        Boards boards = boardsRepository.findByBoardId(boardId);
        Like like = likeRepository.findByLikeUserIdAndLikeBoardId(users, boards);

        likeRepository.delete(like);

        return like.getLikeId();
    }

    //게시판을 삭제 해주는
    @Transactional
    public void deleteLikeBoard(Long boardId){
        Boards boards = boardsRepository.findByBoardId(boardId);
        likeRepository.deleteByLikeBoardId(boards);

    }
}
