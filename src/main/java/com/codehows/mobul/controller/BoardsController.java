package com.codehows.mobul.controller;

import com.codehows.mobul.dto.BoardsDTO;
import com.codehows.mobul.entity.Boards;
import com.codehows.mobul.service.BoardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/writer")
    public String writerForm(){return  "boards/writer";}

//--
    // BoardsFIleFormDTO를 model에 담아서 뷰로 전달
    // value, return 확인필요
//    @GetMapping(value = "/new")
//    public String writer(Model model) {
//        model.addAttribute("boardsFileDTO", new BoardsFormDTO());
//        return "boards/writer";
//    }

    // /write 페이지 보이기 - 데이터 가져오기 - boards/writer.html에서
    @GetMapping("/write")
    public String boardWriteForm(){
        // 어떤 html파일로 이동할지
        return "boards/writer";
    }

    // 게시물 등록
    @PostMapping("/write")
    public String boardsWrite(Boards boards) {
        boardsService.write(boards);

        return "boards/writer";
    }




    // 게시물+파일 업로드 수정중
//    @PostMapping("/write")
//    public String boardsWrite(Boards boards, Model model, MultipartFile file) throws Exception {
//        boardsService.write(boards, file);
//        model.addAttribute("message", "글 작성이 완료되었습니다");
//        model.addAttribute("searchUrl", "/board/list");
//
////        return "boards/writer";
//          return "message";
//    }






//    @PostMapping("/writerpro")
//    public String boardWritePro(B)

//    @PostMapping(value = "/new")
//    public String boardsNew(@Valid BoardsFormDTO boardsFormDTO, BindingResult bindingResult,
//                            Model model) {
//        if (bindingResult.hasErrors()) {      // 상품 등록 시 필수 값 없으면 상품 페이지로 전환
//            return "boards/boardsForm";
//        }
//
//        try {
//            boardsService.saveBoards(boardsFormDTO);
//
//        } catch (Exception e) {
//            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다");
//            return "boards/boardsForm";
//        }
//        return "redirect:/";        // 정상 등록 -> 메인페이지 이동
//    }
//


}
