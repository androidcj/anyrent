/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.dto 
 * @author: caojun 
 * @date: 2018年5月17日 下午10:15:49 
 */
package com.javacj.dto;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 地铁站
 * @Package: com.javacj.dto 
 * @author: caojun 
 * @date: 2018年5月17日 下午10:15:49 
 */
public class SubwayDto {
    private Long id;
    private String name;
    private String cityEnName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCityEnName() {
		return cityEnName;
	}
	public void setCityEnName(String cityEnName) {
		this.cityEnName = cityEnName;
	}
    
    
    
}
