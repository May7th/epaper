package com.oyun.media.epaper.utils;

import com.oyun.media.epaper.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-08-29 13:57
 **/
public class UserUtil {
    public static User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
