package com.codehows.mobul.service;

import com.codehows.mobul.dto.UsersDTO;
import com.codehows.mobul.entity.Users;
import com.codehows.mobul.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    public UsersDTO signIn(@ModelAttribute UsersDTO usersDTO) {
        // 1. 회원이 입력한 Id로 DB에서 조회
        // 2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
        Optional<Users> byUserId = authRepository.findByUserId(usersDTO.getUserId());
        if (byUserId.isPresent()) {  // 조회 결과가 있다 ( 해당 아이디를 가진 유저가 있다 )
            Users users = byUserId.get();       // 옵셔널로 감싸진 객체를 한 꺼풀 벗겨내는

            if (users.getUserPassword().equals(usersDTO.getUserPassword())) {
                UsersDTO dto = UsersDTO.toUsersDTO(users);  // 비밀번호가 일치하면 entity -> dto 변환 후 리턴
                return dto;
            } else return null;         // 비밀번호 불일치 로그인 실패 ~

        } else {
            return null;   // 조회 결과가 없다 ( 해당 아이디를 가진 유저가 없다 )


        }
    }




    public void save(UsersDTO usersDTO) {
        // 1. dto -> entity변환
        // 2. repository의 save 메서드 호출
        Users users = Users.authSignUp(usersDTO); // 매개 변수 타입 usersDTO 객체
        authRepository.save(users);
        // reprository의 save메서드 호출 ( 조건 . entity객체를 넘겨줘야 함

    }

    public List<UsersDTO> findAll() {
        List<Users> usersList = authRepository.findAll();  //리스트에는 반드시 엔티티가 온다
        List<UsersDTO> usersDTOList = new ArrayList<>();
        for (Users users: usersList){
            usersDTOList.add(UsersDTO.toUsersDTO(users));
            UsersDTO usersDTO = UsersDTO.toUsersDTO(users);
            usersDTOList.add(usersDTO);
            
            //윗 작업을 길게 쓰면 이렇게 된다
//            UsersDTO usersDTO = UsersDTO.toUsersDTO(users);
//            usersDTOList.add(usersDTO);
        }
        return usersDTOList;
    }

    public void deleteById(String userId) {

        authRepository.deleteById(userId);
    }

//-da--
    //기존 kakao 가입자인지 확인
    //@Transactional(readOnly=true)
    public Users alreadyUser(String userId){
       // Users users = authRepository.findByUserId(userId).get();
        Users users = authRepository.findByUserId(userId).orElseGet(()->{
            return new Users();
        });
        return users;
    }

}



