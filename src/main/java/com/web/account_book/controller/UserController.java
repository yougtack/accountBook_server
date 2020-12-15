package com.web.account_book.controller;

import com.web.account_book.config.auth.PrincipalDetails;
import com.web.account_book.model.User;
import com.web.account_book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping(value = {"","/"})
    public User login(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpSession session){
        session.setAttribute("asd", principalDetails.getUser().getEmail());
        System.out.println("session.get"+session.getAttribute("asd"));
        return principalDetails.getUser();
    }

    @PostMapping(value = "/join")
    public ModelAndView join(User user) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/templates/login.html");

        System.out.println("회원가입 진행 : " + user);
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole("ROLE_USER");
        userRepository.save(user);
        return mav;
    }
}
