/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.config 
 * @author: caojun 
 * @date: 2018年5月6日 下午1:55:09 
 */
package com.javacj.config;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.javacj.security.AuthProvider;
import com.javacj.security.LoginAuthFailHandler;
import com.javacj.security.LoginUrlEntryPoint;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.config 
 * @author: caojun 
 * @date: 2018年5月6日 下午1:55:09 
 */

@EnableWebSecurity
@EnableGlobalMethodSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	/* (non-Javadoc)
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		//http权限控制
		http.authorizeRequests().antMatchers("/admin/login").permitAll().antMatchers("/static/**").permitAll()
		.antMatchers("/user/login").permitAll()
		.antMatchers("/admin/**").hasRole("ADMIN").antMatchers("/user/**").hasAnyRole("ADMIN","USER")
		.antMatchers("/api/user/**").hasAnyRole("ADMIN","USER").and().formLogin()
		.loginProcessingUrl("/login")		//配置角色登录处理入口
		.failureHandler(authenticationFailureHandler())
		.and().logout().logoutUrl("/logout")
		.logoutSuccessUrl("/logout/page")
		.deleteCookies("JSESSIONID")
		.invalidateHttpSession(true)
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(loginUrlEntryPoint())
		.accessDeniedPage("/403");
		
		http.csrf().disable();   //先关闭防御策略
		http.headers().frameOptions().sameOrigin();   
	}
	
	/**
	 * 自定义认证策略
	 */
	@Autowired
	public void configGlobal(AuthenticationManagerBuilder auth){
		
		try {
//			auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN").and();
			auth.authenticationProvider(authProvider()).eraseCredentials(true);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Bean 
	public AuthProvider authProvider(){
		return new AuthProvider();
	}
	
	@Bean 
	public LoginUrlEntryPoint loginUrlEntryPoint(){
		//默认走用户登录
		return new LoginUrlEntryPoint("/user/login");
	}
	
	
	@Bean
	public LoginAuthFailHandler authenticationFailureHandler(){
		return new LoginAuthFailHandler(loginUrlEntryPoint());
	}
	
	
}
