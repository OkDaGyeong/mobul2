package com.codehows.mobul.repository;

import com.codehows.mobul.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CommentsRepository extends JpaRepository<Comments, Long> {
}
