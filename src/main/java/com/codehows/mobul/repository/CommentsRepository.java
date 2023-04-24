package com.codehows.mobul.repository;

import com.codehows.mobul.entity.Boards;
import com.codehows.mobul.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    // select * from comments where board_id=? order by comment_id desc;  // 댓글 내림차순으로
    List<Comments> findAllByBoardsOrderByCommentIdDesc(Boards boards); // Jpa 문법 board_id 기준으로 조회 할때는 매개변수로 클래스가 들어가야 한다

    Comments findByCommentId(Long commentId);


    int countByBoards(Boards boards);
}
