package com.codehows.mobul.controller;

import com.codehows.mobul.dto.BoardsDTO;
import com.codehows.mobul.dto.BoardsFormDTO;
import com.codehows.mobul.entity.Boards;
import com.codehows.mobul.entity.BoardsFile;
import com.codehows.mobul.repository.BoardsFileRepository;
import com.codehows.mobul.repository.BoardsRepository;
import com.codehows.mobul.service.BoardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


//board 뒤에 붙게 대표 주소 /board 입력
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardsController {
//    @Autowired
    private final BoardsService boardsService;

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

//경원--
    @GetMapping("/comment")
    public String commentForm(){return "boards/comment";}

//    @GetMapping("/writer")
//    public String writerForm(){return  "boards/writer";}

    @GetMapping("/admin")
    public String adminForm(){return  "boards/admin";}



    // /write 페이지 보이기 - 데이터 가져오기 - boards/writer.html에서
    @GetMapping("/writer")     // writerForm -> boardWriteForm
    public String writerForm(Model model){
        model.addAttribute("boardsFormDTO", new BoardsFormDTO());

        return  "/boards/writer";
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
            return "boards/writer";
        }
        return "redirect:/";
    }




    //----상세페이지
    @GetMapping(value="/comment/{boardId}")
    public String boardDtl(Model model, @PathVariable("boardId") Long boardId){
        boardsService.updateView(boardId);
        BoardsFormDTO boardsFormDTO = boardsService.getBoardDtl(boardId);
        model.addAttribute("boardsForm", boardsFormDTO);
        return "/boards/comment";
    }



    // 게시글 삭제
    @GetMapping("/comment/delete/{boardId}")
    public String deleteBoard(@PathVariable Long boardId){

        // id 조회
        Boards board = boardsRepository.findByBoardId(boardId);
        //                .orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + boardId));

        // file 객체 삭제
        List<BoardsFile> files = boardsFileRepository.findByFileBoardNumOrderByFileIdAsc(board);   //
        if(!files.isEmpty()){
            boardsFileRepository.deleteAll(files);
        }

        // Boards 객체 삭제
        boardsRepository.delete(board);

        return "redirect:/";
    }





//    @Autowired
//    BoardsFileRepository boardsFileRepository;
//    @Autowired
//    BoardsRepository boardsRepository;


//    @GetMapping(value="/comment/drop")
//    public String dropBoard(){
//
//    }
//    @GetMapping(value="/comment/delete")
//    public String boardDelete2(){
//
//        Boards boards = boardsRepository.findByBoardId(3L);
//        boardsFileRepository.deleteAllByFileBoardNum(boards);
//        boardsService.boardDelete(3L);
//
//
//        // 게시물 삭제 후 게시물 리스트
//        return "redirect:/";
//    }
//

}
