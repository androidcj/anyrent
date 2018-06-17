/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.security 
 * @author: caojun 
 * @date: 2018年5月7日 下午8:31:41 
 */
package com.javacj.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.javacj.entity.User;
import com.javacj.service.user.IUserService;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 自定义认证实现
 * @Package: com.javacj.security 
 * @author: caojun 
 * @date: 2018年5月7日 下午8:31:41 
 */
public class AuthProvider implements AuthenticationProvider{

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Autowired
	private IUserService userService;
	
	private final Md5PasswordEncoder md5Encoder = new Md5PasswordEncoder();   //md5加密器
	
	
	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		String username = auth.getName();    //获取用户名
		String inputPassword = auth.getCredentials()+"";
		User user = userService.findUserByName(username);
		if(user ==null ){   //如果查不到用户  则抛出异常
			throw new AuthenticationCredentialsNotFoundException("用户名不存在");
		}
		
		//通过md5进行密码验证   以ID做为加盐
		if(this.md5Encoder.isPasswordValid(user.getPassword(), inputPassword, user.getId())){
			 return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			
		}
		throw new BadCredentialsException("认证错误");
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		//支持所有的认证类
		return true;
	}

}
