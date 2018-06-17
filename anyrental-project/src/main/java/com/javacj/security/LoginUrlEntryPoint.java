/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.security 
 * @author: caojun 
 * @date: 2018年5月7日 下午10:45:15 
 */
package com.javacj.security;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 基于角色的登录控制器
 * @Package: com.javacj.security 
 * @author: caojun 
 * @date: 2018年5月7日 下午10:45:15 
 */
public class LoginUrlEntryPoint extends LoginUrlAuthenticationEntryPoint{

	
	private final Map<String,String> authEntryPointMap;
	private PathMatcher pathMatcher = new AntPathMatcher();
	
	public LoginUrlEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
		// TODO Auto-generated constructor stub
		authEntryPointMap = new HashMap<String,String>();
		authEntryPointMap.put("/user/**", "/user/login");
		//管理员登录入口映射
		authEntryPointMap.put("/admin/**", "/admin/login");
	}
	/* 根据请求跳转到指定的页面  父类是默认使用loginFrimUrl
	 * @see org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint#determineUrlToUseForThisRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@Override
	protected String determineUrlToUseForThisRequest(
			HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) {
		// TODO Auto-generated method stub
		
		String uri = request.getRequestURI().replace(request.getContextPath(), "");
		for (Map.Entry<String, String> authEntry : this.authEntryPointMap.entrySet()) {
			if(this.pathMatcher.match(authEntry.getKey(), uri)){
				//如果匹配则跳转到对应的入口页面
				return authEntry.getValue();
			}
		}
		
		
		return super.determineUrlToUseForThisRequest(request, response, exception);
	}
}
