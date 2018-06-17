/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.controller 
 * @author: caojun 
 * @date: 2018年5月5日 下午9:54:37 
 */
package com.javacj.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javacj.base.ApiResponse;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.controller 
 * @author: caojun 
 * @date: 2018年5月5日 下午9:54:37 
 */
@Controller
public class HomeController {
	@GetMapping(value = {"/", "/index"})
	public String index(Model model){
		System.out.println("index=========================");
		model.addAttribute("name", "androidcj");
		return "index";
	} 
	
	@GetMapping("/404")
    public String notFoundPage() {
        return "404";
    }

    @GetMapping("/403")
    public String accessError() {
        return "403";
    }

    @GetMapping("/500")
    public String internalError() {
        return "500";
    }

    @GetMapping("/logout/page")
    public String logoutPage() {
        return "logout";
    }
	

}
