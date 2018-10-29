package com.oyun.media.epaper.utils;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 工具类.
 * 
 * @since 1.0.0 2017年3月28日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
public class MD5Util {

	/** 
     * 获取该输入流的MD5值 
     ** @return
     * @throws NoSuchAlgorithmException 
     * @throws IOException 
     */  
    public static String getMD5(MultipartFile multipartfile) throws NoSuchAlgorithmException, IOException {

            CommonsMultipartFile commonsmultipartfile = (CommonsMultipartFile) multipartfile;
            DiskFileItem diskFileItem = (DiskFileItem) commonsmultipartfile.getFileItem();
            File file = diskFileItem.getStoreLocation();
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;

    }  

}
