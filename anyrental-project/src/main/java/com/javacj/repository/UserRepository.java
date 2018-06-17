/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.repository 
 * @author: caojun 
 * @date: 2018年5月5日 下午6:41:16 
 */
package com.javacj.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.javacj.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	User findByName(String username);     //通过名字来查找
	
	
}
