/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.security 
 * @author: caojun 
 * @date: 2018年5月8日 下午9:09:00 
 */
package com.javacj.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 登录验证失败 处理器
 * @Package: com.javacj.security 
 * @author: caojun 
 * @date: 2018年5月8日 下午9:09:00 
 */
public class LoginAuthFailHandler extends SimpleUrlAuthenticationFailureHandler{
	
	private final LoginUrlEntryPoint loginUrlEntryPoint;

	/**   
	 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
	 * 
	 * @Package: com.javacj.security 
	 * @author: caojun  
	 * @date: 2018年5月8日 下午9:10:49 
	 */
	public LoginAuthFailHandler(LoginUrlEntryPoint loginUrlEntryPoint) {
		this.loginUrlEntryPoint = loginUrlEntryPoint;
	}
	
	
	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String targetUrl = this.loginUrlEntryPoint.determineUrlToUseForThisRequest(request, response, exception);
		System.out.println("exception====="+exception.getMessage());
		targetUrl = targetUrl+"?"+exception.getMessage();
		super.setDefaultFailureUrl(targetUrl);
		super.onAuthenticationFailure(request, response, exception);
		
	}
	
}
