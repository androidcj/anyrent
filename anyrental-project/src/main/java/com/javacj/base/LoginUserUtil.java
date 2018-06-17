/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.base 
 * @author: caojun 
 * @date: 2018年5月18日 下午3:00:50 
 */
package com.javacj.base;

import org.springframework.security.core.context.SecurityContextHolder;

import com.javacj.entity.User;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.base 
 * @author: caojun 
 * @date: 2018年5月18日 下午3:00:50 
 */
public class LoginUserUtil {
	public static User load(){
		//加载当前用户
		Object pricinpal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(pricinpal!=null && pricinpal instanceof User){
			return (User)pricinpal;
		}
		return null;
	}
	
	public static Long getLoginUserId(){
		User user = load();
		if(user == null){
			return -1L;
		}
		return user.getId();
	}
	
}
