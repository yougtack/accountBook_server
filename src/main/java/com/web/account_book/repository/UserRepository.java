package com.web.account_book.repository;

import com.web.account_book.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);  //sql에 쿼리같은 형식임 이 문장은 select * from user where email = (보내준 email값)과 같음
    //https://jobc.tistory.com/120 설명이 잘되어있는 블로그

    @Modifying
    @Query(value = "UPDATE user SET profile_name = ?1, profile_path = ?2 WHERE username = ?3", nativeQuery = true)
    void update(String profile_name, String profile_path, String username);

    @Query(value = "SELECT profile_path FROM user WHERE username = ?1", nativeQuery = true)
    String findByProfile_path(String username);
}