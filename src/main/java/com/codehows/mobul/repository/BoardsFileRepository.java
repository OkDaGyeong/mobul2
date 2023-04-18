package com.codehows.mobul.repository;

import com.codehows.mobul.entity.Boards;
import com.codehows.mobul.entity.BoardsFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BoardsFileRepository extends JpaRepository<BoardsFile, Long> {
    List<BoardsFile> findByFileBoardNumOrderByFileIdAsc(Boards boardId);


}
