package com.oyun.media.epaper.config.authentication;

import com.oyun.media.epaper.Filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-09-01 22:18
 **/
@Configuration
@EnableResourceServer
public class MyResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler marchAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler marchAuthenticationFailureHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();

        validateCodeFilter.setAuthenticationFailureHandler(marchAuthenticationFailureHandler);

        http.addFilterBefore(validateCodeFilter,UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**","/libs/**", "/fonts/**", "/index","/path/**","/font/**","/images/**").permitAll()
                .antMatchers("/druid/**","/validate/code/image","/upload/image/**").permitAll()
                .antMatchers("/oauth/token").permitAll()
                .antMatchers("/v2/api-docs",
                        "/swagger-resources/configuration/ui",
                        "/swagger-resources",
                        "/swagger-resources/configuration/security",
                        "/swagger-ui.html").permitAll()
                .antMatchers("/epaper/**").permitAll()
                .antMatchers("/admins/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login_page")
                .successHandler(marchAuthenticationSuccessHandler)
                .failureHandler(marchAuthenticationFailureHandler).loginProcessingUrl("/login")
                .usernameParameter("username").passwordParameter("password").permitAll()
                .and()
                .logout()
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        System.out.println("logout success!");
                    }
                });
        http.csrf().disable();
    }
}