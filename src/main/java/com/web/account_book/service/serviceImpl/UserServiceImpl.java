package com.web.account_book.service.serviceImpl;

import com.web.account_book.model.entity.User;
import com.web.account_book.repository.UserRepository;
import com.web.account_book.service.UserService;
import com.web.account_book.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public void update_profile(MultipartHttpServletRequest multipartHttpServletRequest, String username) throws IOException {
        List<MultipartFile> multipartFiles = multipartHttpServletRequest.getFiles("profile");
        if (!multipartFiles.isEmpty()) {
            for (MultipartFile filePart : multipartFiles) {
                String file_path = "/member_images/"+FileUtil.fileInsert(filePart, 2);
//                userRepository.update(file_path, username);
            }
        }
    }
}
