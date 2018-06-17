/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.service 
 * @author: caojun 
 * @date: 2018年5月7日 下午8:36:34 
 */
package com.javacj.service.user;

import com.javacj.dto.UserDTO;
import com.javacj.entity.User;
import com.javacj.service.ServiceResult;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 用户服务接口
 * @Package: com.javacj.service 
 * @author: caojun 
 * @date: 2018年5月7日 下午8:36:34 
 */
public interface IUserService {
	User findUserByName(String username);
	
	ServiceResult<UserDTO> findById(Long userId);
	
}
