/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.repository 
 * @author: caojun 
 * @date: 2018年5月18日 上午10:59:13 
 */
package com.javacj.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.javacj.entity.HouseDetail;


public interface HouseDetailRepository extends CrudRepository<HouseDetail, Long>{
	public HouseDetail findByHouseId(Long houseId);
	public List<HouseDetail> findAllByHouseIdIn(List<Long> houseIds);
	
}
