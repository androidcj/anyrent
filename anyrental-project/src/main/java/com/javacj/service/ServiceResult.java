/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.service 
 * @author: caojun 
 * @date: 2018年5月18日 上午11:09:24 
 */
package com.javacj.service;


/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 单对象通用返回接口
 * @Package: com.javacj.service 
 * @author: caojun 
 * @date: 2018年5月18日 上午11:09:24 
 */
public class ServiceResult <T>{
    private boolean success;
    private String message;
    private T result;
	
    public ServiceResult(boolean success) {
        this.success = success;
    }

    public ServiceResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ServiceResult(boolean success, String message, T result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
    
    public static <T> ServiceResult<T> success() {
        return new ServiceResult<>(true);
    }
    
    public static <T> ServiceResult<T> of(T result) {
        ServiceResult<T> serviceResult = new ServiceResult<>(true);
        serviceResult.setResult(result);
        return serviceResult;
    }
    
    public static <T> ServiceResult<T> notFound() {
        return new ServiceResult<>(false, Message.NOT_FOUND.getValue());
    }
    
    
    public static <T> ServiceResult<T> notSuccess() {
        return new ServiceResult<>(false, Message.NOT_SUCCESS.getValue());
    }
    
    public enum Message {
        NOT_FOUND("Not Found Resource!"),
        NOT_LOGIN("User not login!"),
        NOT_SUCCESS("Some Thing Wrong!");
        private String value;

        Message(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
