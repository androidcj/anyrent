/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.dto 
 * @author: caojun 
 * @date: 2018年5月15日 下午10:57:42 
 */
package com.javacj.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.dto 
 * @author: caojun 
 * @date: 2018年5月15日 下午10:57:42 
 */
public class SupportAddressDto {
	private Long id;
	
	@JsonProperty(value="belong_to")
	private String belongTo;
	
	@JsonProperty(value="en_name")
	private String enName;
	
	@JsonProperty(value="cn_name")
	private String cnName;
	
	private String level;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
}
