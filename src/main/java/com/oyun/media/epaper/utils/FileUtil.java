package com.oyun.media.epaper.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @program: epaper
 * @description: 文件工具类
 * @author: changzhen
 * @create: 2018-08-10 09:40
 **/

@Component
@Log4j2
public class FileUtil {

    @Value("${spring.servlet.multipart.location}")
    private String multipartLocation;


    /**
     * 获取去掉"-"的uuid
     * @return
     */
    public String getUUID(){
        UUID uuid36 =UUID.randomUUID();

        String uuid = uuid36.toString().replace("-", "");

        return uuid;
    }

    public String saveFileToDir(MultipartFile file,String fileName){

        Date date = new Date();
        String path =new SimpleDateFormat("yyyy-MM-dd").format(date)+"/";
        File f = new File(multipartLocation+"/"+path);
        if(!f.exists()){
            f.mkdirs();
        }
        File target = new File(multipartLocation+"/"+path,fileName);
        try {
            file.transferTo(target);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return path+fileName;
    }

    public String getSuffix(String fileName){
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf("."));
        } else {
            log.warn("该文件没有后缀名");
            return ".bak";
        }
    }

    public boolean deleteFile(String deletePath){

        boolean success = new File(deletePath).delete();

        if (success){
            log.info("文件删除成功！");
            return success;
        }else {
            log.error("删除文件出错！");
            return success;
        }

    }

}
