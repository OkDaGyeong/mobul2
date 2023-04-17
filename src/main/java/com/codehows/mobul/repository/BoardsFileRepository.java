package com.codehows.mobul.repository;

import com.codehows.mobul.entity.BoardsFile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardsFileRepository extends JpaRepository<BoardsFile, Long> {
//    List<BoardsFile> findByBoardIdOrderByIdAsc(Long boardId);

}
