/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.web.form 
 * @author: caojun 
 * @date: 2018年6月7日 下午10:09:35 
 */
package com.javacj.web.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.web.form 
 * @author: caojun 
 * @date: 2018年6月7日 下午10:09:35 
 */
public class DatatableSearch {
	private int drew;
	private int start;
	private int length;
	private Integer status;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTimeMin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTimeMax;

    private String city;
    private String title;
    private String direction;
    private String orderBy;
	public int getDrew() {
		return drew;
	}
	public void setDrew(int drew) {
		this.drew = drew;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTimeMin() {
		return createTimeMin;
	}
	public void setCreateTimeMin(Date createTimeMin) {
		this.createTimeMin = createTimeMin;
	}
	public Date getCreateTimeMax() {
		return createTimeMax;
	}
	public void setCreateTimeMax(Date createTimeMax) {
		this.createTimeMax = createTimeMax;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	
}
