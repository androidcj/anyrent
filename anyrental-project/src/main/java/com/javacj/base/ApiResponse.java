/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.base 
 * @author: caojun 
 * @date: 2018年5月6日 上午10:59:52 
 */
package com.javacj.base;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * API的格式封装
 * @Package: com.javacj.base 
 * @author: caojun 
 * @date: 2018年5月6日 上午10:59:52 
 */
public class ApiResponse {
	private int code;
	private String message;
	private Object data;
	private boolean more;
	
	public ApiResponse(int code,String message,Object data){
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public ApiResponse(){
		this.code = Status.SUCCESS.getCode();
		this.message = Status.SUCCESS.getStandardMessage();
	}
	
	public static ApiResponse ofMessage(int code,String message){
		return new ApiResponse(code,message,null);
	}
	
	public static ApiResponse ofSuccess(Object data){
		return new ApiResponse(Status.SUCCESS.getCode(),Status.SUCCESS.getStandardMessage(),data);
	}
	
	public static ApiResponse ofStatus(Status status) {
        return new ApiResponse(status.getCode(), status.getStandardMessage(), null);
    }
	 
	 
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public boolean isMore() {
		return more;
	}
	public void setMore(boolean more) {
		this.more = more;
	}
	
	public enum Status{
		SUCCESS(200,"OK"),
		BAD_REQUEST(400,"BAD_REQUEST"),
		INTERNAL_SERVER_ERROR(500,"INTERNAL_SERVER_ERROR"),
		NOT_FOUND(404, "Not Found"),
		NOT_VALID_PARAM(40005, "Not valid Params"),
        NOT_SUPPORTED_OPERATION(40006, "Operation not supported"),
        NOT_LOGIN(50000, "Not Login");
		
		private int code;
		private String standardMessage;
		Status(int code,String message){
			this.code = code;
			this.standardMessage = message;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getStandardMessage() {
			return standardMessage;
		}
		public void setStandardMessage(String standardMessage) {
			this.standardMessage = standardMessage;
		}
		
		
	}
	
	
}
