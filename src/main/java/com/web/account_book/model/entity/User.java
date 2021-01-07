package com.web.account_book.model.entity;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
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

    @Builder
    public User(String username, String password, String email, String role, String provide, String providerId, Timestamp createDate){
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provide = provide;
        this.providerId = providerId;
        this.createDate = createDate;
    }
}