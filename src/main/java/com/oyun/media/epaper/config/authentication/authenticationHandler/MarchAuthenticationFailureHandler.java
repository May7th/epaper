package com.oyun.media.epaper.config.authentication.authenticationHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oyun.media.epaper.exception.ValidateCodeException;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-08-29 11:29
 **/
@Component("marchAuthenticationFailureHandler")
@Slf4j
public class MarchAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.info("login failure");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        StringBuffer sb = new StringBuffer();
        sb.append("{\"status\":\"error\",\"msg\":\"");
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            sb.append("用户名或密码输入错误，登录失败!");
        } else if (e instanceof DisabledException) {
            sb.append("账户被禁用，登录失败，请联系管理员!");
        } else if(e instanceof ValidateCodeException) {
            sb.append(e.getMessage());
        }else {
            sb.append("登录失败!");
        }
        sb.append("\"}");
        httpServletResponse.getWriter().write(sb.toString());

    }
}
