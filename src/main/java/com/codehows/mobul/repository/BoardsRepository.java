package com.codehows.mobul.repository;

import com.codehows.mobul.entity.Boards;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardsRepository extends JpaRepository<Boards, Long> {

    Boards save(Boards boards);

    // 게시글 조회
//    Boards findByBoardTitle(String boardTitle);
    Boards findByBoardId(Long boardId);

    Boards deleteByBoardId(Long boardId);
    //게시글 목록 조회
    List<Boards> findAll(); //게시글 모두 불러오기 -d

    //게시글 검색(제목으로 검색)
    Page<Boards> findByBoardTitleContaining(String searchTitle, Pageable pageable); //페이징 기능을 사용하기 위해 Page<Boards>사용

    //본문으로 검색
    Page<Boards> findByBoardContentContaining(String searchContent, Pageable pageable);

    // 해시태그 검색
    Page<Boards> findByBoardTagContaining(String searchTag, Pageable pageable);

    //조회수 변경
    @Modifying
    @Query("update Boards p set p.boardView = p.boardView + 1 where p.boardId = :board_id AND p.boardId IS NOT NULL")
    int updateBoardView(@Param("board_id") Long boardId);



    //좋아요 증가
//    @Modifying
//    @Query("update Boards p set p.boardLike = p.boardLike + 1 where p.boardId = :board_id AND p.boardId IS NOT NULL")
//    int updateBoardLike(@Param("board_id") Long boardId);

}
