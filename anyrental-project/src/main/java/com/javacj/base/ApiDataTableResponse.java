/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.base 
 * @author: caojun 
 * @date: 2018年6月7日 下午9:54:35 
 */
package com.javacj.base;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * dataTable格式定义
 * @Package: com.javacj.base 
 * @author: caojun 
 * @date: 2018年6月7日 下午9:54:35 
 */
public class ApiDataTableResponse extends ApiResponse{
	private int draw;
	
	//字段名称必须写成这样
	private long recordsTotal;      //总数
	private long recordsFiltered;	//分页
	/**   
	 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
	 * 
	 * @Package: com.javacj.base 
	 * @author: caojun  
	 * @date: 2018年6月7日 下午10:07:59 
	 */
	
	
	public ApiDataTableResponse(ApiResponse.Status status){
		
		this(status.getCode(),status.getStandardMessage(),null);
	}
	
	
	public ApiDataTableResponse(int code,String message,Object data) {
		// TODO Auto-generated constructor stub
		super(code,message,data);
	}
	public int getDraw() {
		return draw;
	}
	public void setDraw(int draw) {
		this.draw = draw;
	}
	public long getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public long getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	
}
