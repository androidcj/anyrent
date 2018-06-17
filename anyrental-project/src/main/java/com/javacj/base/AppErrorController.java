/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.base 
 * @author: caojun 
 * @date: 2018年5月6日 下午12:06:39 
 */
package com.javacj.base;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.base 
 * @author: caojun 
 * @date: 2018年5月6日 下午12:06:39 
 */
@Controller
public class AppErrorController implements ErrorController{

	/* (non-Javadoc)
	 * @see org.springframework.boot.autoconfigure.web.ErrorController#getErrorPath()
	 */
	private static final String ERRORPATH = "/error";
	private ErrorAttributes errorAttributes;
	
	
	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return ERRORPATH;
	}
	
	@Autowired
	public AppErrorController(ErrorAttributes errorAttributes){
		this.errorAttributes = errorAttributes;
	}

	/**
	 * web页面错误处理
	 * 
	 * 
	 */
	@RequestMapping(value=ERRORPATH,produces = "text/html")
	public String errorPageHandler(HttpServletRequest request,HttpServletResponse response){
		int status =  response.getStatus();
		  switch (status) {
          case 403:
              return "403";
          case 404:
              return "404";
          case 500:
              return "500";
      }
      return "index";
	}
	
	
	//除web页面之外的错误处理
	@RequestMapping(value=ERRORPATH)
	@ResponseBody
	public ApiResponse errorApiHandler(HttpServletRequest request) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);

        Map<String, Object> attr = this.errorAttributes.getErrorAttributes(requestAttributes, false);
        int status = getStatus(request);

        return ApiResponse.ofMessage(status, String.valueOf(attr.getOrDefault("message", "error")));
    }
	
	private int getStatus(HttpServletRequest request) {
        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (status != null) {
            return status;
        }

        return 500;
    }
	
}
