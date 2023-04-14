package com.codehows.mobul.dto;

import com.codehows.mobul.entity.Users;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor     // 모든 필드를 매개변수로 하는 생성자 들을 자동으로 만들어줌
public class UsersDTO {

        private String userId;
        private String userPassword;
        private String userPhone;

        // entity -> dto

        public static UsersDTO toUsersDTO(Users users){
                UsersDTO usersDTO = new UsersDTO();
                usersDTO.setUserId(users.getUserId());
                usersDTO.setUserPassword(users.getUserPassword());
                usersDTO.setUserPhone(users.getUserPhone());
                return usersDTO;
        }

}
