package com.web.account_book.service.serviceImpl;

import com.web.account_book.model.entity.User;
import com.web.account_book.repository.UserRepository;
import com.web.account_book.service.UserService;
import com.web.account_book.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public int update_profile(MultipartHttpServletRequest multipartHttpServletRequest, String username) throws IOException {
        User userEntity = userRepository.findByUsername(username);
        if(!userEntity.getProfile_name().equals("default.png")){
            FileUtil.fileDelete(userEntity.getProfile_name(), 2);
        }

        List<MultipartFile> multipartFiles = multipartHttpServletRequest.getFiles("profile");
        if (!multipartFiles.isEmpty()) {
            for (MultipartFile filePart : multipartFiles) {
                String file_name = FileUtil.fileInsert(filePart, 2);
                String file_path = "/member_images/"+ file_name;
                userRepository.update(file_name, file_path, username);
                return 1;
            }
        }else{
                String file_name = "default.png";
                String file_path = "/member_images/"+ file_name;
                userRepository.update(file_name, file_path, username);
                return 1;
        }
        return 0;
    }
}
