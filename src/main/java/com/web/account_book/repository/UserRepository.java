package com.web.account_book.repository;

import com.web.account_book.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String email);  //sql에 쿼리같은 형식임 이 문장은 select * from user where email = (보내준 email값)과 같음
    User findByEmail(String email);
    //https://jobc.tistory.com/120 설명이 잘되어있는 블로그

}