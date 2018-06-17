/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.repository 
 * @author: caojun 
 * @date: 2018年5月17日 下午10:52:54 
 */
package com.javacj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.javacj.entity.SubWay;
import com.javacj.entity.SubwayStation;

public interface SubwayRepository extends CrudRepository<SubWay,Long>{
	
	@Query("select o from SubWay o where cityEnName = ?1")
	 List<SubWay>findAllSubwayByCity(@Param("cityEnName") String cityEnName);
	
	@Query("select o from SubwayStation o where subwayId=?1")
	List<SubwayStation> findAllStationBySubway(@Param("subwayId") Long subwayId);
}
