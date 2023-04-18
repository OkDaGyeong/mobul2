package com.codehows.mobul.controller;

import com.codehows.mobul.dto.BoardsDTO;
import com.codehows.mobul.dto.BoardsFormDTO;
import com.codehows.mobul.entity.Boards;
import com.codehows.mobul.service.BoardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
//import org.springframework.web.multipart.MultipartFile;

//import java.util.concurrent.ExecutionException;

//board 뒤에 붙게 대표 주소 /board 입력
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardsController {
    @Autowired
    private BoardsService boardsService;


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

//경원--
    @GetMapping("/comment")
    public String commentForm(){return "boards/comment";}

//    @GetMapping("/writer")
//    public String writerForm(){return  "boards/writer";}

    @GetMapping("/admin")
    public String adminForm(){return  "boards/admin";}

//--
    // BoardsFIleFormDTO를 model에 담아서 뷰로 전달
    // value, return 확인필요
//    @GetMapping(value = "/new")
//    public String writer(Model model) {
//        model.addAttribute("boardsFileDTO", new BoardsFormDTO());
//        return "boards/writer";
//    }

    // /write 페이지 보이기 - 데이터 가져오기 - boards/writer.html에서
    @GetMapping("/writer")     // writerForm -> boardWriteForm
    public String writerForm(Model model){
        model.addAttribute("boardsFormDTO", new BoardsFormDTO());

        return  "/boards/writer";
    }

    //게시물 등록
    @PostMapping("/writer")
    public String boardsWrite(@Valid BoardsFormDTO boardsFormDTO, BindingResult bindingResult, Model model,
                              @RequestParam("boardsFile") List<MultipartFile> fileList, HttpSession session){
        if(bindingResult.hasErrors()){ return "/boards/writer"; }

        try{
            boardsService.saveBoard(boardsFormDTO, fileList,session);
        } catch (Exception e){
            model.addAttribute("errorMessage", "파일 등록 중 에러가 발생하였습니다");
            return "/boards/writer";
        }
        return "redirect:/";
    }


    //----상세페이지 불러오기
    @GetMapping(value="/comment/{boardId}")
    public String boardDtl(Model model, @PathVariable("boardId") Long boardId){
        boardsService.updateView(boardId);
        BoardsFormDTO boardsFormDTO = boardsService.getBoardDtl(boardId);
        model.addAttribute("boardsForm", boardsFormDTO);
        return "/boards/comment";
    }




}
