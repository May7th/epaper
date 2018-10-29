package com.oyun.media.epaper.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * @program: epaper
 * @description: 验证码异常
 * @author: changzhen
 * @create: 2018-08-30 10:09
 **/
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
