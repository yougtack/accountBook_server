package com.web.account_book.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@WebListener
public class SessionConfig{
    private static final Map<String, String> sessions = new HashMap<>();


    public synchronized static void getSessionIdCheck(String type, String compareId, HttpSession session, HttpServletRequest request, HttpServletResponse response){
        System.out.println("type:"+type+" compareId:"+compareId);
        sessions.put(type, compareId);
        for(String key : sessions.keySet()){
            String value = sessions.get(key);
            if(session.getAttribute("username").equals(value)){
                removeSessionForDoubleLogin(value, request, response);
            }
        }
    }

    private static void removeSessionForDoubleLogin(String userId, HttpServletRequest request, HttpServletResponse response){
        System.out.println("remove userId : " + userId);
        if(userId != null && userId.length() > 0){
            System.out.println("로그인이 중복되어 로그아웃을 진행합니다.");
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null){
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
        }
    }
}
