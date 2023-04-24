package com.codehows.mobul.repository;

import com.codehows.mobul.entity.Boards;
import com.codehows.mobul.entity.Like;
import com.codehows.mobul.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository  extends JpaRepository<Like,Long> {
    /* likeGet - exist */
    boolean existsByLikeUserIdAndLikeBoardId(Optional<Users> users, Boards boards);

    /* likeGet - find */
    Like findByLikeUserIdAndLikeBoardId(Optional<Users> users, Boards boards);

    /* likeSize - count */
    Long countByLikeBoardId(Boards boards);

    //Like save(Like like);


}
