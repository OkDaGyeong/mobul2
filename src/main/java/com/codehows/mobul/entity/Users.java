package com.codehows.mobul.entity;

import com.codehows.mobul.constant.Role;
import com.codehows.mobul.dto.UsersDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "users")
public class Users {

    @Id         //pk 설정
    @Column(name="user_id", nullable = false, length = 20)
    private String userId;

    @Column(nullable = false, length = 20)
    private String userPassword;

    @Column(length = 20)
    private String userPhone;

    // 유저 생성 메서드  dto -> entity
    public static Users authSignUp(UsersDTO usersDTO){
        Users users = new Users();
        users.setUserId(usersDTO.getUserId());
        users.setUserPassword(usersDTO.getUserPassword());
        users.setUserPhone(usersDTO.getUserPhone());
        return  users;
    }

}
