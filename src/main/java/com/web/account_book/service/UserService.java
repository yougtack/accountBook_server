package com.web.account_book.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

public interface UserService {
    void update_profile(MultipartHttpServletRequest multipartHttpServletRequest, String username) throws IOException;
}
