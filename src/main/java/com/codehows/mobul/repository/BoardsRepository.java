package com.codehows.mobul.repository;

import com.codehows.mobul.entity.Boards;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardsRepository extends JpaRepository<Boards, Long>, QuerydslPredicateExecutor<Boards> {

    Boards save(Boards boards);

    List<Boards> findByBoardId(Long boardId);

    // 게시글 조회
//    Boards findByBoardTitle(String boardTitle);
//    Boards findByBoardId(Long boardId);

    //게시글 목록 조회
    List<Boards> findAll(); //게시글 모두 불러오기 -d

    //게시글 검색(제목으로 검색)
    Page<Boards> findByBoardTitleContaining(String searchTitle, Pageable pageable); //페이징 기능을 사용하기 위해 Page<Boards>사용

    //본문으로 검색
    Page<Boards> findByBoardContentContaining(String searchContent, Pageable pageable);
}
