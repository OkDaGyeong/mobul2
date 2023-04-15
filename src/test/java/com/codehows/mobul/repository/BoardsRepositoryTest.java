package com.codehows.mobul.repository;

import com.codehows.mobul.entity.Boards;
import com.codehows.mobul.entity.QBoards;
import com.codehows.mobul.entity.Users;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class BoardsRepositoryTest {


    @Autowired
    BoardsRepository boardsRepository;

    @Autowired
    AuthRepository authRepository;

    @PersistenceContext
    EntityManager em;


    public void createBoardList(){
        for(int i=1; i<=10; i++ ){
            Boards board1 = new Boards();
            board1.setBoardId(1L + i);
            board1.setBoardTitle("첫 번째 게시물" + i);
            board1.setBoardContent("첫 번째 게시물 내용" + i);
            board1.setBoardWriter("작성자1" + i);
            board1.setBoardTag("태그1"+ i);
            boardsRepository.save(board1);
        }
    }

    @Test
    @DisplayName("상품 저장 테트")
    public void createBoardTest(){
        Boards board = new Boards();
        board.setBoardContent("테스트 상품");
        board.setBoardTitle("와따매~");
        board.setBoardWriter("작성자");
        board.setBoardDate(LocalDateTime.now());
        Boards saveBoards = boardsRepository.save(board);
    }

    @Test
    @DisplayName("게시판 아이디 조회")
    public void findByBoardIdTest(){
        this.createBoardList();
        List<Boards> boardsList = boardsRepository.findByBoardId(1L);
        for(Boards boards: boardsList){
            System.out.println(boards.toString());
        }
    }

    @Test
    @DisplayName("QueryDsl")
    public void queryDslTest() {
        this.createBoardList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QBoards qBoards = QBoards.boards;
        JPAQuery<Boards> query = queryFactory.selectFrom(qBoards)
            .where(qBoards.boardContent.eq("dd"))
            .where(qBoards.boardTitle.eq("와따야 "))
            .orderBy(qBoards.boardTitle.desc());
        List<Boards> boardsList = query.fetch();
        for (Boards boards : boardsList) {
            System.out.println(boards.toString());
        }

        public void createBoardList2 () {
            for (int i = 1; i <= 5; i++) {
                Boards boards = new Boards();
                boards.setBoardId(1L + i);
                boards.setBoardWriter("돼지새끼" + i);
                boards.setBoardContent("게시글 내용" + i);
                boards.setBoardDate(LocalDateTime.now());
                boards.setBoardTitle("안녕하세용");
            }
            for (int i = 6; i < 10; i++) {
                Boards boards = new Boards();
                boards.setBoardId(1L + i);
                boards.setBoardWriter("돼지새끼" + i);
                boards.setBoardContent("게시글 내용" + i);
                boards.setBoardDate(LocalDateTime.now());
                boards.setBoardTitle("안녕하세용");
            }

        }
    }


}
