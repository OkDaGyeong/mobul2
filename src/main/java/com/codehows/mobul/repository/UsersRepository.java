package com.codehows.mobul.repository;

import com.codehows.mobul.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository의 제네릭 타입으로 <Entity, PK의 타입> 을 지정해주면 Spring Data JPA는 자동으로 스프링의 빈으로 등록
public interface UsersRepository extends JpaRepository<Users, String> {


    Users findByUserId(String userId);

}
