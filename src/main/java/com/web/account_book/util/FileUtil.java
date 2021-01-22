package com.web.account_book.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class FileUtil {
    final static HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    final static String root_path = request.getSession().getServletContext().getRealPath("/");
    final static String attach_path = "static/images/";
    final static String attach_path_member = "member_images/";

    public static String fileInsert(MultipartFile filePart, int type) throws IOException{
        UUID uuid = UUID.randomUUID();
        String file_path = "";
        String exc = filePart.getOriginalFilename().substring(filePart.getOriginalFilename().lastIndexOf(".")+1);

        if(type == 1){
            file_path = root_path+attach_path+uuid+"."+exc;
        }else if(type == 2) {
            file_path = root_path+attach_path_member+uuid+"."+exc;
        }
        File saveFile = new File(file_path);
        filePart.transferTo(saveFile);

        return uuid+"."+exc;
    }

    public static void fileDelete(String file_name, int type) {
        String file_path = "";

        if(type == 1){
            file_path = root_path + attach_path + file_name;
        }else if(type == 2){
            file_path = root_path + attach_path_member + file_name;
        }

        File file = new File(file_path);
        file.delete();
    }

    public static String transToJson(ArrayList<String> path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(path);

        return jsonStr;
    }
}
