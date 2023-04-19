package com.codehows.mobul.entity;

import com.codehows.mobul.constant.Role;
import com.codehows.mobul.dto.UsersDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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


    // 양방향 매핑 부모 설정
    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Boards> boardEntityList = new ArrayList<>();


    // 유저 생성 메서드  dto -> entity
    public static Users authSignUp(UsersDTO usersDTO){
        Users users = new Users();
        users.setUserId(usersDTO.getUserId());
        users.setUserPassword(usersDTO.getUserPassword());
        users.setUserPhone(usersDTO.getUserPhone());
        return  users;
    }

}
