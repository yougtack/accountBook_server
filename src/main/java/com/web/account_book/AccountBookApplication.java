package com.web.account_book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableJpaAuditing
public class AccountBookApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(AccountBookApplication.class, args);
    }
}
