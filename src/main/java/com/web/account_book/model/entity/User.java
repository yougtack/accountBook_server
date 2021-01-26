package com.web.account_book.model.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id // primary key
    private String username;

    private String password;
    private String email;
    private String role; //ROLE_USER, ROLE_ADMIN

    private String provide;
    private String providerId;

    @CreationTimestamp
    private Timestamp createDate;

    private String profile_path;
    private String profile_name;

    @Builder
    public User(String username, String password, String email, String role, String provide, String providerId, Timestamp createDate,
                String profile_path, String profile_name){
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provide = provide;
        this.providerId = providerId;
        this.createDate = createDate;
        this.profile_path = profile_path;
        this.profile_name = profile_name;
    }
}