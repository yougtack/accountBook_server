package com.web.account_book.controller;

import com.web.account_book.config.SessionConfig;
import com.web.account_book.config.auth.PrincipalDetails;
import com.web.account_book.model.UserInfoModel;
import com.web.account_book.model.entity.User;
import com.web.account_book.repository.UserRepository;
import com.web.account_book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping(value = {"","/"})

    public UserInfoModel login(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpSession session, HttpServletRequest request, HttpServletResponse response){
        if(session.getAttribute("username") == null){
            System.out.println("세션에 아무값도 없습니다. 임시로 username에 empty를 넣습니다.");
            session.setAttribute("username", "empty");
        }
        SessionConfig.getSessionIdCheck("username", principalDetails.getUser().getUsername(), session, request, response);

        UserInfoModel userInfoModel = new UserInfoModel();
        if(principalDetails != null) {
            userInfoModel.setUsername(principalDetails.getUser().getUsername());
            userInfoModel.setEmail(principalDetails.getUser().getEmail());
            userInfoModel.setRole(principalDetails.getUser().getRole());
            userInfoModel.setProfile_path(userRepository.findByProfile_path(principalDetails.getUser().getUsername()));

            session.setAttribute("username", principalDetails.getUser().getUsername());
            session.setMaxInactiveInterval(60 * 30);
        }

        return userInfoModel;
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
        user.setProfile_path("/member_images/default.png");
        user.setProfile_name("default.png");

        userRepository.save(user);
        return mav;
    }

    @GetMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/templates/login.html");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return mav;
    }

    @PutMapping(value = "/profile/{username}")
    public int update_profile(MultipartHttpServletRequest multipartHttpServletRequest, @PathVariable String username) throws IOException {
        return userService.update_profile(multipartHttpServletRequest, username);
    }
}
