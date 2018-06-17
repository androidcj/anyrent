/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.anyrentalproject.entity 
 * @author: caojun 
 * @date: 2018年5月5日 下午6:28:09 
 */
package com.javacj.entity;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.javacj.AnyrentalProjectApplicationTests;
import com.javacj.entity.User;
import com.javacj.repository.UserRepository;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.anyrentalproject.entity 
 * @author: caojun 
 * @date: 2018年5月5日 下午6:28:09 
 */
public class UserRepositoryTest extends AnyrentalProjectApplicationTests{
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testFindOne(){
		User user = userRepository.findOne(1L);
		System.out.println("user=="+user.getName());
	}
	
	
	
}
