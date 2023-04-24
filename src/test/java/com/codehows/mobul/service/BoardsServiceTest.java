package com.codehows.mobul.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class BoardsServiceTest {
//
//    @Autowired
//    BoardsService boardsService;
//
//    @Autowired
//    BoardsRepository boardsRepository;
//
//    @Autowired
//    UsersRepository usersRepository;
//
//    @Test
//    @DisplayName("게시글 등록 테스트")
//    @WithMockUser(username = "admin", roles ="ADMIN")
//    void saveItem() throws Exception{
//        Users user = new Users();
//        user.setUserId("test");
//        user.setUserPassword("test");
//        user.setUserPhone("000000");
//        usersRepository.save(user);
//
//
//
//        BoardsFormDTO boardsFormDTO = new BoardsFormDTO();
//        boardsFormDTO.setBoardTitle("테스트제목");
//        boardsFormDTO.setBoardContent("테스트내용");
//        boardsFormDTO.setBoardWriter("테스트 작성자");         // boards 테이블의 boards_id
//        boardsFormDTO.setBoardTag("테스트 해시태그");
//
//        Long boardId = boardsService.saveBoards(boardsFormDTO, multipartFileList);
//
//        Boards boards = boardsRepository.findById(boardId)
//                        .orElseThrow(EntityNotFoundException::new);
//
//        assertEquals(boardsFormDTO.getBoardTitle(), boards.getBoardTitle());
//        assertEquals(boardsFormDTO.getBoardContent(), boards.getBoardContent());
//        assertEquals(boardsFormDTO.getBoardWriter(), boards.getBoardWriter());
//        assertEquals(boardsFormDTO.getBoardTag(), boards.getBoardTag());
//
//
//    }



}
