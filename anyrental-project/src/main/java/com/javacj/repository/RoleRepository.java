/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 角色数据
 * @Package: com.javacj.repository 
 * @author: caojun 
 * @date: 2018年5月7日 下午9:07:28 
 */
package com.javacj.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.javacj.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
	List<Role> findRolesByUserId(Long userId);    //通过userid查询Roles
}
