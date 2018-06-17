/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.service 
 * @author: caojun 
 * @date: 2018年5月16日 下午8:39:53 
 */
package com.javacj.service;

import java.util.List;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 通用结果返回结构
 * @Package: com.javacj.service 
 * @author: caojun 
 * @date: 2018年5月16日 下午8:39:53 
 */
public class ServiceMultiResult<T> {
	private Long total;
	private List<T> result;
	
	public ServiceMultiResult(long total,List<T> result){
		this.total = total;
		this.result = result;
		
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<T> getResult() {
		return result;
	}
	public void setResult(List<T> result) {
		this.result = result;
	}
	
	//返回结果集长度
	public int getResultSize(){
		if(this.result==null){
			return 0;
		}else{
			return this.result.size();
		}
	}
	
}
