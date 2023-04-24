package com.codehows.mobul.controller;

import com.codehows.mobul.dto.UsersDTO;
import com.codehows.mobul.entity.Users;
import com.codehows.mobul.model.KakaoProfile;
import com.codehows.mobul.model.OAuthToken;
import com.codehows.mobul.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping(value="/kakao")
public class KakaoController {
    @Autowired
    private AuthService authService;

    @Value("${cos.key}")
    private String cosKey; //kakao 회원가입시 임시 비밀번호 (절대 노출되면 안됨)

    @ResponseBody
    @PostMapping(value="/code")         // 로그인
    public String kakaoLogin(Model model){
        return "redirect:/"; //templates폴더를 기준으로 뷰의 위치와 이름을 반환
    }


    @GetMapping(value="/code")
    public String getCode(@RequestParam String code, HttpSession session) {
        //post방식으로 key=value데이터를 요청해야함
        RestTemplate rt = new RestTemplate();

        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); //key=value형태로 넘어감을 아렬줌

        //HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "f16a9a399cece2acaaf3110b8761e417");
        params.add("redirect_uri", "http://localhost:9095/kakao/code");
        params.add("code", code);

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기(exchange함수를 쓰기위해-형식맞춰주려고)
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers); //body데이터와 header값을 가진 엔티티가 된다

        //Http 요청하기 - Post방식으로 - 그리고 response변수의 응답 받음
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class);

        //json 데이터를 java object로 처리하기위해 object형으로 바꿈
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("카카오 엑세스토큰 : " + oauthToken.getAccess_token());

        //--
        //사용자 정보 요청
        //post방식으로 key=value데이터를 요청해야함
        RestTemplate rt2 = new RestTemplate();

        //HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); //key=value형태로 넘어감을 아렬줌

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기(exchange함수를 쓰기위해-형식맞춰주려고)
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers2); //body데이터와 header값을 가진 엔티티가 된다

        //Http 요청하기 - Post방식으로 - 그리고 response변수의 응답 받음
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class);

        //---

        //json 데이터를 java object로 처리하기위해 object형으로 바꿈
        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        //user오브젝트가 가진 정보
        System.out.println("사용자 아이디(번호) : " + kakaoProfile.getId());
        System.out.println("이메일 : " + kakaoProfile.getKakao_account().getHas_email());
//-- (강제로 회원가입 시켜버리기~)
        System.out.println("mobul 사용자아이디 : " + "kakao" + kakaoProfile.getId());
        System.out.println("mobul 사용자비밀번호 : " + cosKey);//임시값

//일단막해 되는진 모름 되면 개이득 : db에 들어간당
        Users newUser = new Users();
        newUser.setUserId("kakao" + kakaoProfile.getId());
        newUser.setUserPassword(cosKey);
        UsersDTO newUsersDTO = UsersDTO.toUsersDTO(newUser);

//비가입자는 회원가입 진행
        Users originUser = authService.alreadyUser(newUser.getUserId());
        if (originUser.getUserId() == null) {
            System.out.println("가입정보가 없습니다. no data user");
            authService.save(newUsersDTO); //회원가입하기
        }
//기존 가입자일 경우 로그인으로 처리
        System.out.println("자동로그인을 진행합니다 auto login");
        authService.signIn(newUsersDTO);//로그인되는거 맞나,,
        session.setAttribute("userId", newUsersDTO.getUserId()); // 로그인 한 회원의 아이디 정보를 세션에다 담아준다

        return "redirect:/";

    }
}