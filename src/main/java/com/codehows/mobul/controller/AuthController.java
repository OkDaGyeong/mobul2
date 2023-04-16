package com.codehows.mobul.controller;

import com.codehows.mobul.dto.UsersDTO;
import com.codehows.mobul.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping(value="/auth") // url에 /auth로 들어오는 요청을 이 AuthController가 처리하도록함
public class AuthController {


    private final AuthService authService;  // 생성자 주입 방식 AuthController


    @GetMapping(value="/signin")         // 로그인
    public String signInForm(Model model){
        return "auth/signin"; //templates폴더를 기준으로 뷰의 위치와 이름을 반환
    }



    @PostMapping(value="/signin")       // 로그인
    public String signIn(@ModelAttribute UsersDTO usersDTO, HttpSession session){   // ModelAttribute 자동으로 값들을 받아오고 넣어주고 하는 어노테이션
        System.out.println("AuthController.signIn");
        System.out.println("usersDTO = " + usersDTO);
        UsersDTO signInResult = authService.signIn(usersDTO);
        if (signInResult != null) {// 로그인 성공
            session.setAttribute("userId", signInResult.getUserId()); // 로그인 한 회원의 아이디 정보를 세션에다 담아준다

            return "redirect:/";
        } else {
            // 로그인 실패
            return "/auth/signin";
        }


    }


    @GetMapping(value="/signup")       // 회원가입
    public String signUpForm(Model model){
        return "auth/signup";
    }


    @PostMapping(value="/signup")       // 회원가입
    public String signUp(@ModelAttribute UsersDTO usersDTO){   // ModelAttribute 자동으로 값들을 받아오고 넣어주고 하는 어노테이션
        System.out.println("AuthController.signup");
        System.out.println("usersDTO = " + usersDTO); //  작성한 값들을 콘솔로 확인
        //DTO --> entity 객체
        authService.save(usersDTO);


        return "redirect:/";

    }

    @GetMapping("/list")
    public  String findAll(Model model){        //리퀘스트에 영역에 있는 데이터들을 객체들을 화면에 뿌려주는 스프링에서는 model
        List<UsersDTO> usersDTOList = authService.findAll(); // 여러개의 데이터를 가져 올 때는 리스트로 쓴다
        //어떠한 html로 가져갈 데이터가 있다면 model 사용
        model.addAttribute("usersList", usersDTOList);
        return "/auth/list";
    }


    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable String userId){
        authService.deleteById(userId);
        return "redirect:/";         // 리다이렉트 뒤에는 주소가 온다

    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();       // session 값 삭제
        return "redirect:/";

    }
}








