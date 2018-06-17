/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.user 
 * @author: caojun 
 * @date: 2018年5月7日 下午10:36:26 
 */
package com.javacj.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.user 
 * @author: caojun 
 * @date: 2018年5月7日 下午10:36:26 
 */
@Controller
public class UserController {
	@GetMapping("/user/login")
    public String loginPage() {
        return "user/login";
    }

    @GetMapping("/user/center")
    public String centerPage() {
        return "user/center";
        
    }
}
