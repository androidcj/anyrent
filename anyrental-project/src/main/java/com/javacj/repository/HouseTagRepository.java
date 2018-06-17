/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.repository 
 * @author: caojun 
 * @date: 2018年5月18日 上午11:03:46 
 */
package com.javacj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.javacj.entity.HouseTag;


public interface HouseTagRepository extends CrudRepository<HouseTag, Long> {
	public List<HouseTag> findAllByHouseId(Long houseId);
	
	@Query("select housetag from HouseTag housetag where name=:tag and houseId = :houseId")
	public HouseTag findByNameAndHouseId(@Param("tag") String tag,@Param("houseId") Long houseId);
	
	public List<HouseTag> findAllByHouseIdIn(List<Long> houseIds);
	
}
