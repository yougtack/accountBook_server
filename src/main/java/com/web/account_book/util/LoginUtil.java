package com.web.account_book.util;

import com.web.account_book.util.enums.HttpStatusEnums;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginUtil {

    public static HttpStatusEnums login_check(String username, HttpSession session, HttpServletResponse response){
        if(session.getAttribute("username") == null){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return HttpStatusEnums.Unauthorized_401;

        }else if(!session.getAttribute("username").equals(username)){
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return HttpStatusEnums.Forbidden_403;
        }
        return HttpStatusEnums.Success_200;
    }
}
