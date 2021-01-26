package com.web.account_book.model;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class TestUserModel {
    private String username;
    private String email;

    public TestUserModel(String username, String email){
        this.username = username;
        this.email = email;
    }
}
