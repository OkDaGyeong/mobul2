package com.codehows.mobul.service;

import com.codehows.mobul.dto.CommentsDTO;
import com.codehows.mobul.dto.UsersDTO;
import com.codehows.mobul.entity.Boards;
import com.codehows.mobul.entity.Comments;
import com.codehows.mobul.entity.Users;
import com.codehows.mobul.repository.AuthRepository;
import com.codehows.mobul.repository.BoardsRepository;
import com.codehows.mobul.repository.CommentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor  // 파이널일 때 자동생성자 해줌
@Transactional
public class CommentsService {


    private final CommentsRepository commentsRepository;
    private final BoardsRepository boardsRepository;


    public Long save(CommentsDTO commentsDTO) {
        //부모 엔티티 조회를 했는데 부모 엔티티가 조회가 되지 않는다면 null을 리턴해서 컨트롤러에서 응답을 주고 
        // 부모 엔티티가 조회 하면 값을 저장한다
        Boards boards = boardsRepository.findByBoardId(commentsDTO.getBoardId()); //커맨트 디티오에 보드아이디 필더에 들어있는 보드 아이디를 꺼내온다
//        Users users1 = authRepository.findByUserId(users.getUserId()).get();
        if (boards != null) {
            Comments comments = Comments.toSaveEntity(commentsDTO, boards);   // 이렇게 쓰는 이유가  디비 값들을 최대한 보호하기 위해

            return commentsRepository.save(comments).getCommentId();

        } else {
            return null;
        }

        //builder

    }

    //코멘트의 댓글 리스트를 보여 주기 위해 findAll 활용

    public List<CommentsDTO> findAll(Long boardId) {
        // select * from comments where board_id=? order by comment_id desc;  // 댓글 내림차순으로
        Boards boards =  boardsRepository.findByBoardId(boardId); // boards 의 boardId를 찾는다
        List<Comments> commentsList =  commentsRepository.findAllByBoardsOrderByCommentIdDesc(boards); // commentsRepository에서 작성한 메서드를 호출 ( Jpa 문법 쿼리 메소드)

        // Entity List -> DTO List
        List<CommentsDTO> commentsDTOList = new ArrayList<>();
        for (Comments comments : commentsList){
            CommentsDTO commentsDTO = CommentsDTO.toCommentsDTO(comments, boardId);
            System.out.println("commentsDTO = " + commentsDTO);
            commentsDTOList.add(commentsDTO);

        }
        return commentsDTOList;
        // 쿼리를 위해 메서드 정의 했고 조건에 엔티티 부모 가 있어야해서 부모 엔티티가 매개변수로 넘어가고 그러기 위해 부모 매개변수를 조회할 값이 필요 하다


    }
}

