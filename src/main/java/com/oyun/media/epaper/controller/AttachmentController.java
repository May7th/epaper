package com.oyun.media.epaper.controller;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @program: epaper
 * @description: 附件控制器
 * @author: changzhen
 * @create: 2018-06-27 23:10
 **/
@RestController
@Log4j2
public class AttachmentController {

    @Value("${file.upload.path}")
    private String filePath;

    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file") MultipartFile file){
            try {
                if (file.isEmpty()) {
                    return "文件为空";
                }
                // 获取文件名
                String fileName = file.getOriginalFilename();
                System.out.println(fileName);                // 获取文件的后缀名
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                System.out.println(suffixName);
                // 设置文件存储路径
                System.out.println(ResourceUtils.getURL("classpath:").getPath());
                System.out.println(ClassUtils.getDefaultClassLoader().getResource("").getPath());
                String path = filePath + fileName + suffixName;

                File dest = new File(path);
                // 检测是否存在目录
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();// 新建文件夹
                }
                file.transferTo(dest);// 文件写入
                return path;
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "上传失败";
    }
}
