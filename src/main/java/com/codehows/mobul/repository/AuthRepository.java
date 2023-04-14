package com.codehows.mobul.repository;

import com.codehows.mobul.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository의 제네릭 타입으로 <Entity, PK의 타입> 을 지정해주면 Spring Data JPA는 자동으로 스프링의 빈으로 등록
public interface AuthRepository extends JpaRepository<Users, String> {
    //아이디로 회원 정보 조회 (select * from users where user_id=?)
    Optional<Users> findByUserId(String userId);






}
