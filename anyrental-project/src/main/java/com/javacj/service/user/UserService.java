/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.service.user 
 * @author: caojun 
 * @date: 2018年5月7日 下午8:38:19 
 */
package com.javacj.service.user;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.javacj.dto.UserDTO;
import com.javacj.entity.Role;
import com.javacj.entity.User;
import com.javacj.repository.RoleRepository;
import com.javacj.repository.UserRepository;
import com.javacj.service.ServiceResult;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.service.user 
 * @author: caojun 
 * @date: 2018年5月7日 下午8:38:19 
 */
@Service
public class UserService implements IUserService{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	/* (non-Javadoc)
	 * @see com.javacj.service.user.IUserService#findUserByName(java.lang.String)
	 */
	
	private ModelMapper modelmapper = new ModelMapper();
	
	@Override
	public User findUserByName(String username) {
		// TODO Auto-generated method stub
		User user = userRepository.findByName(username);
		if(user ==null){
			return null;
		}
		List<Role> roleList = roleRepository.findRolesByUserId(user.getId());
		if(roleList ==null || roleList.isEmpty()){
			throw new DisabledException("非法权限");
		}
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		roleList.forEach(role ->authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName())));
		user.setAuthorityList(authorities);
		return user;
	}
	/* (non-Javadoc)
	 * @see com.javacj.service.user.IUserService#findById(java.lang.Long)
	 */
	@Override
	public ServiceResult<UserDTO> findById(Long userId) {
		// TODO Auto-generated method stub
		User user = userRepository.findOne(userId);
		if(user == null){
			return ServiceResult.notFound();
		}
		UserDTO userdto = modelmapper.map(user, UserDTO.class);
		return ServiceResult.of(userdto);
	}

}
