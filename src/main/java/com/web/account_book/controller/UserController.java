package com.web.account_book.controller;

import com.web.account_book.config.auth.PrincipalDetails;
import com.web.account_book.model.entity.User;
import com.web.account_book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping(value = {"","/"})
    public String login(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpSession session){
        if(principalDetails != null){
            session.setAttribute("username", principalDetails.getUser().getUsername());
            session.setMaxInactiveInterval(60 * 30);
            return principalDetails.getUser().getUsername()+", "+principalDetails.getUser().getEmail();
        }else{
            return "로그인이 되지 않아 정보를 불러 올 수 없습니다.";
        }
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

    @GetMapping(value = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }
}
