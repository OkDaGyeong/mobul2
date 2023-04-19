package com.codehows.mobul.controller;


import com.codehows.mobul.dto.UsersDTO;
import com.codehows.mobul.entity.Users;
import com.codehows.mobul.repository.AuthRepository;
import com.codehows.mobul.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
@RequestMapping(value="/auth") // url에 /auth로 들어오는 요청을 이 AuthController가 처리하도록함
public class AuthController{



    private final AuthService authService;  // 생성자 주입 방식 AuthController

    @Autowired
    private AuthRepository authRepository;


    @GetMapping(value="/signin")         // 로그인
    public String signInForm(Model model){
        return "auth/signin"; //templates폴더를 기준으로 뷰의 위치와 이름을 반환
    }



    @PostMapping(value="/signin")       // 로그인
    public ModelAndView signIn(@ModelAttribute UsersDTO usersDTO, HttpSession session, Model model) {   // ModelAttribute 자동으로 값들을 받아오고 넣어주고 하는 어노테이션
        ModelAndView mav = new ModelAndView();
        UsersDTO signInResult = authService.signIn(usersDTO);   // 로그인한 유저 정보를 받아온다
        if (signInResult != null) { // 로그인 성공
            session.setAttribute("userId", signInResult.getUserId()); //로그인 한 회원 정보를 세션에 담아준다
            mav.setViewName("redirect:/");   //메인 페이지로 돌아간다
        } else { //로그인 실패
            mav.addObject("error", "로그인 실패. 아이디와 비번 확인 바람"); // 에러 메시지 모델 추가
            mav.setViewName("/auth/signin");  // signin 페이지로 이동한다

            System.out.println("AuthController.signIn");
            System.out.println("usersDTO = " + usersDTO);
            return mav;         // 모델과 뷰 정보를 반환 로그인 실패 해서 다시 로그인 페이지로 이동한다

        }
        return mav;  //모델과 뷰 정보를 반환한다 성공시 반환한다
    }



    @GetMapping(value="/signup")       // 회원가입
    public String signUpForm(Model model){
        return "auth/signup";
    }


    @PostMapping(value="/signup")       // 회원가입
    public ModelAndView signUp(@ModelAttribute UsersDTO usersDTO, @RequestParam("userPasswordConfirm") String userPasswordConfirm, Model model){   // ModelAttribute 자동으로 값들을 받아오고 넣어주고 하는 어노테이션
        ModelAndView mav = new ModelAndView();
        Optional<Users> optionalExistingUser = authRepository.findByUserId(usersDTO.getUserId());
        if(optionalExistingUser.isPresent()) {  // 만약 값이 없으면
            Users existingUser = optionalExistingUser.get();  //옵셔널의 값을 벗기겄다
            if(existingUser != null){
                mav.addObject("errorMessage", "이미 존재하는 회원");
                mav.setViewName("/auth/signup");    // 에러메시지를 출력하고 다시 회원가입 페이지로 돌아온다
                return mav;
            }
        }
        //비밀번호 같은지 확인 메서드
        if(!usersDTO.getUserPassword().equals(userPasswordConfirm)){
            mav.addObject("passwordError", "비밀번호 불일치");
            mav.setViewName("/auth/signup");
            return mav;
        }
        authService.save(usersDTO);
        mav.setViewName("redirect:/auth/signin");
        System.out.println("AuthController.signup");
        System.out.println("usersDTO = " + usersDTO); //  작성한 값들을 콘솔로 확인
        authService.save(usersDTO);
        System.out.println(usersDTO.toString());


        return mav;


    }
/*
    @PostMapping("/signup")
    public ResponseEntity<Users> signUp(@RequestBody Users users){
        Optional<Users> existingUser =                 authRepository.findByUserId(users.getUserId());
        if (existingUser != null){
            throw new BadRequestException("This email is already in use");  //사용중인 메시지 출력
        }
        Users savedUesr = authRepository.save(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUesr);
    }
*/



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








