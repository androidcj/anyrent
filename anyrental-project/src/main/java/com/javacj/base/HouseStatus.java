/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.base 
 * @author: caojun 
 * @date: 2018年6月9日 下午2:15:49 
 */
package com.javacj.base;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.base 
 * @author: caojun 
 * @date: 2018年6月9日 下午2:15:49 
 */
public enum HouseStatus {
	NOT_AUDITED(0),   //为审核
	PASS(1),     //审核通过
	RENTED(2),    //已出租
	DELETED(3);  //逻辑删除
	
	public int value;
	
	HouseStatus(int value){
		this.value = value;
		
	}
	
	   public int getValue() {
	        return value;
	    }
	
	
	
//	public enum houseStatus{
//		houseok(400);
//		
//		public int code;
//		houseStatus(int code){
//			this.code = code;
//		}
//		
//		public void setCode(int code){
//			this.code = code;
//		}
//		public int getCode(){
//			return code;
//		}
//		
//		
//	}
	
}
