package com.oyun.media.epaper.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.oyun.media.epaper.Filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 安全配置类.
 * @author changzhen
 */
// 启用方法安全设置
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String KEY = "oyun.com";

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationSuccessHandler marchAuthenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler marchAuthenticationFailureHandler;

	@Autowired
	private DruidDataSource dataSource;

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

    @Qualifier("userServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

	@Bean
	public AuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	    authenticationProvider.setUserDetailsService(userDetailsService);
	    authenticationProvider.setPasswordEncoder(passwordEncoder);
	    return authenticationProvider;
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository(){

		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();

		tokenRepository.setDataSource(dataSource);

//		tokenRepository.setCreateTableOnStartup(true);

		return tokenRepository;
	}

	/**
     * 自定义配置
     */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();

		validateCodeFilter.setAuthenticationFailureHandler(marchAuthenticationFailureHandler);

	    http.addFilterBefore(validateCodeFilter,UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers("/css/**", "/js/**","/libs/**", "/fonts/**", "/index","/path/**").permitAll()
				.antMatchers("/druid/**","/validate/code/image","/upload/image/**").permitAll()
				.antMatchers("/oauth/token").permitAll()
				.antMatchers("/v2/api-docs",
						"/swagger-resources/configuration/ui",
						"/swagger-resources",
						"/swagger-resources/configuration/security",
						"/swagger-ui.html").permitAll()
	            .antMatchers("/admins/**").hasRole("ADMIN")
				.anyRequest().authenticated()
				.and()
	            .formLogin().loginPage("/login_page")
					.successHandler(marchAuthenticationSuccessHandler)
					.failureHandler(marchAuthenticationFailureHandler).loginProcessingUrl("/login")
					.usernameParameter("username").passwordParameter("password").permitAll()
	            .and()
				.rememberMe()
					.tokenRepository(persistentTokenRepository())
					.tokenValiditySeconds(3600*24*7)
					.userDetailsService(userDetailsService)
				.and()
				.logout()
					.logoutSuccessHandler(new LogoutSuccessHandler() {
						@Override
						public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
							System.out.println("logout success!");
						}
					});
		http.csrf().disable();
//	    http.csrf().ignoringAntMatchers("/druid/**");
//	    http.csrf().ignoringAntMatchers("/swagger-ui.html");
//	    http.headers().frameOptions().sameOrigin();
	}

	/**
	 * 认证信息管理
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(userDetailsService);
	    auth.authenticationProvider(authenticationProvider());
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
