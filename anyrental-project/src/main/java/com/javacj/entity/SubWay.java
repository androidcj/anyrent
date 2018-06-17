/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.entity 
 * @author: caojun 
 * @date: 2018年5月17日 下午10:19:52 
 */
package com.javacj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.entity 
 * @author: caojun 
 * @date: 2018年5月17日 下午10:19:52 
 */
@Entity
@Table(name = "subway")
public class SubWay {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	 private String name;

	    @Column(name = "city_en_name")
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
