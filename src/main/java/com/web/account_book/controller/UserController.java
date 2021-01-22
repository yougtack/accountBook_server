package com.web.account_book.controller;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
    public UserInfoModel login(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpSession session){
        UserInfoModel userInfoModel = new UserInfoModel();
        if(principalDetails != null) {
            userInfoModel.setUsername(principalDetails.getUser().getUsername());
            userInfoModel.setEmail(principalDetails.getUser().getEmail());
            userInfoModel.setRole(principalDetails.getUser().getRole());
            userInfoModel.setProfile_path(principalDetails.getUser().getProfile_path());

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

    @PutMapping(value = "/profile/{username}")
    public int update_profile(MultipartHttpServletRequest multipartHttpServletRequest, @PathVariable String username) throws IOException {
        System.out.println("프로필 테스트");
        return userService.update_profile(multipartHttpServletRequest, username);
    }
}
