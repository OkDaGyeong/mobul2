package com.codehows.mobul.repository;

import com.codehows.mobul.entity.Boards;
import com.codehows.mobul.entity.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class BoardsRepositoryTest {

//
//    @Autowired
//    BoardsRepository boardsRepository;
//
//    @Autowired
//    UsersRepository usersRepository;
//
//    @PersistenceContext
//    EntityManager em;
//
//    @Test
//    @DisplayName("게시글 저장 테스트")
//    public void createBoardsTest(){
//        Users user = new Users();
//        user.setUserId("test");
//        user.setUserPassword("test");
//        user.setUserPhone("000000");
//		usersRepository.save(user);
//
//        Users us = usersRepository.findByUserId("test");
//
//		Boards boards = new Boards();
//		boards.setBoardTitle("테스트 제목");
//		boards.setBoardContent("테스트 본문");
//		boards.setBoardWriter("테스트 작성자");
//		boards.setBoardTag("테스트 해시태그");
//		boards.setUsers(us);
//        System.out.println(boards.getBoardId());
//		Boards savedBoards = boardsRepository.save(boards);
//
//        Boards bd = boardsRepository.findByBoardTitle("테스트 제목");
//        System.out.println(bd.getBoardId());
//
////        em.flush();
////        em.clear();
//        assertEquals(us.getUserId(), bd.getUsers().getUserId());
//    }
    @Test
    void contextLoads() {
    }

}

//    @DataJpaTest 어노테이션을 통해 JPA 레포지토리 관련 테스트에 필요한 설정이 자동으로 이루어지도록 합니다. 이 테스트에서는 게시글 등록 후 등록된 게시글을 조회하는 테스트를 수행합니다. given, when, then 패턴에 따라 코드가 작성되었으며, 게시글의 필수 정보인 게시글 제목, 내용, 작성자, 작




/*

@SpringBootTest
class BoardsRepositoryTest {


    @Autowired
    UsersRepository usersRepository;

    @Autowired
    BoardsRepository boardsRepository;

    @Test
    public void InsertDummies() {
        Users users = new Users();
        // test 유저 생성
        users.setUserId("test");

        //테스트 케이스에 users 생성
        usersRepository.save(users);
        Users user = usersRepository.findByUserId("test"); // UsersRepository 에 메서드 추상 메서드 작성


        IntStream.rangeClosed(1, 10).forEach(i -> {
            Boards boards = new Boards();
            boards.setBoardTitle("test" + i);
            boards.setUsers(user);

            //create
            boardsRepository.save(boards);
        });
    }
*/

