package com.codehows.mobul.controller;

import com.codehows.mobul.dto.BoardsDTO;
import com.codehows.mobul.dto.BoardsFormDTO;
import com.codehows.mobul.dto.CommentsDTO;
import com.codehows.mobul.entity.Boards;
import com.codehows.mobul.entity.BoardsFile;
import com.codehows.mobul.entity.Users;
import com.codehows.mobul.repository.BoardsFileRepository;
import com.codehows.mobul.repository.BoardsRepository;
import com.codehows.mobul.service.AuthService;
import com.codehows.mobul.service.BoardsService;
import com.codehows.mobul.service.CommentsService;
import com.codehows.mobul.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
//import org.springframework.web.multipart.MultipartFile;



//board 뒤에 붙게 대표 주소 /board 입력
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardsController {

    private final BoardsService boardsService;

    private final LikeService likeService;

    private final AuthService authService;

    private final CommentsService commentsService;

    private final BoardsRepository boardsRepository;

    private final BoardsFileRepository boardsFileRepository;

    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    //html 에서 작성한 걸 controller로 전달하는 방법 중 가장 좋은 방법은 
    // dto라는 클래스를 만들어 객체를 받아서 dto 에서 controller 로 넘겨주는 방법이 가장 좋음
    @PostMapping("/save")
    public String save(@ModelAttribute BoardsDTO boardsDTO) {
        // save.html이랑 name 과 필드 값이 동일하다면  스프링이 해당하는 필드에 대한 setter를 알아서 호출하면서
        // name에 setter의 값들 setter 메서드로 각각 알아서 담아 준다
        System.out.println(boardsDTO);
        return null;
    }



    @GetMapping("/admin")
    public String adminForm(){return  "boards/admin";}



    // /write 페이지 보이기 - 데이터 가져오기 - boards/writer.html에서
//    @GetMapping("/writer")     // writerForm -> boardWriteForm
//    public String writerForm(Model model){
//        model.addAttribute("boardsFormDTO", new BoardsFormDTO());
//
//        return  "/boards/writer";
//    }-- 미로그인 접근 제한
    @GetMapping("/writer")
    public String writerForm(Model model, HttpSession session) {
        // 세션에서 로그인 정보를 가져옴
        String userId = (String) session.getAttribute("userId");
        // 로그인 되어있지 않으면 로그인 페이지로 이동
        if (userId == null || userId.equals("")) {
            return "redirect:/auth/signin";
        }
        model.addAttribute("boardsFormDTO", new BoardsFormDTO());
        return "/boards/writer";
    }


    // 작성페이지
    @PostMapping("/writer")
    public String boardsWrite(@Valid BoardsFormDTO boardsFormDTO, BindingResult bindingResult, Model model,
                              @RequestParam("boardsFile") List<MultipartFile> fileList, HttpSession session){
        if(bindingResult.hasErrors()){ return "/boards/writer"; }

        try{
            boardsService.saveBoard(boardsFormDTO, fileList, session);
        } catch (Exception e){
            model.addAttribute("errorMessage", "파일 등록 중 에러가 발생하였습니다");
            return "boards/writer";
        }
        return "redirect:/";
    }



    // 수정페이지
    @GetMapping(value="/writer/{boardId}")
    public String boardDtlUpdate(Model model, @PathVariable("boardId") Long boardId){

        try {
            BoardsFormDTO boardsFormDTO =boardsService.getBoardDtl(boardId);
            model.addAttribute("boardsFormDTO", boardsFormDTO);
        } catch(EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
//            model.addAttribute("boardsFormDTO", new BoardsFormDTO());    //
            return "boards/writer";
        }
        return "boards/writer";
    }


    @PostMapping(value="/writer/{boardId}")
    public String boardsUpdate(@Valid BoardsFormDTO boardsFormDTO, BindingResult bindingResult,
                               @RequestParam("boardsFile") List<MultipartFile> boardsFileList, Model model){

        if(bindingResult.hasErrors()){
            return "boards/writer";
        }

        try{

            boardsService.updateBoard(boardsFormDTO, boardsFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "게시글 수정 중 에러가 발생하였습니다");
           System.out.println("44"+e);
            return "boards/writer";
        }
        System.out.println("55555555555555555");

        return "redirect:/";
    }



    //----상세페이지
    @GetMapping(value="/comment/{boardId}")
    public String boardDtl(Model model, @PathVariable("boardId") Long boardId ,
                           HttpSession session, HttpServletRequest request, HttpServletResponse response){
        //-- 조회수 무제한증가 방지
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("boardView")){
                    oldCookie = cookie;
                }
            }
        }

        if(oldCookie != null){
            if(!oldCookie.getValue().contains("[" + boardId.toString() + "]")){
                boardsService.updateView(boardId); //조회수 증가
                oldCookie.setValue(oldCookie.getValue() + "_[" + boardId + "]");
                oldCookie.setPath("/"); //왜 주소가 저거지?
                oldCookie.setMaxAge(60*60*24);
                response.addCookie(oldCookie);
            }
        }
        else{
            boardsService.updateView(boardId); //조회수 증가
            Cookie newCookie = new Cookie("boardView","["+boardId+"]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60*60*24);
            response.addCookie(newCookie);
        }

        //-- 게시글 불러오기
        Boards boards = boardsService.findByBoardId(boardId);

        BoardsFormDTO boardsFormDTO = boardsService.getBoardDtl(boardId);

        // 댓글 목록 가져오기
        List<CommentsDTO> commentsDTOList = commentsService.findAll(boardId);
        model.addAttribute("commentsList", commentsDTOList);   //  html에서 사용할 수 있게 값들을 넘겨 준다

        // comment창에서 해시태그 #붙여서 보이게
        String hashTag = boardsFormDTO.getBoardTag();
        if (hashTag.length()>0){
            hashTag = hashTag.substring(0, hashTag.length() - 1);
        }

        hashTag = hashTag.replace(",", " #");
        boardsFormDTO.setBoardTag(hashTag);


        model.addAttribute("boardsForm", boardsFormDTO);

        //--like
        Long likeCount = likeService.findLikeCount(boards);
        model.addAttribute("likeCount", likeCount);

        // 댓글 수 반환
        int commentCount = commentsService.countCommentsByBoard(boards);
        model.addAttribute("commentCount", commentCount);
        System.out.println("횟수 작성" + commentCount);

/*        boards.setBoardId(boardId);
        commentsService.countCommentsByBoard(boards);
        */

        // 로그인 유저 아이디 확인,
        System.out.println("로그인된 유저 id : "+session.getAttribute("userId"));
        String loginUserId = (String)session.getAttribute("userId");

        Optional<Users> users = authService.findByUserId(loginUserId);

        if(loginUserId != null){ // 로그인 했는지 구분
            if(likeService.findLike(users, boards)){ // 게시글에 좋아요를 누른 사람인지 확인
                model.addAttribute("isLiked",true);
            }else {
                model.addAttribute("isLiked", false);
            }
        }else {
            model.addAttribute("isLiked", false);
        }
        return "/boards/comment";
    }

    // 게시글 삭제
    @GetMapping("/comment/delete/{boardId}")
    public String deleteBoard(@PathVariable Long boardId){

        // id 조회
        Boards board = boardsRepository.findByBoardId(boardId);
        // file 객체 삭제
        List<BoardsFile> files = boardsFileRepository.findByFileBoardNumOrderByFileIdAsc(board);   //
        if(!files.isEmpty()){
            boardsFileRepository.deleteAll(files);
        }

        likeService.deleteLikeBoard(boardId);
        boardsRepository.delete(board);

        return "redirect:/";
    }


}
