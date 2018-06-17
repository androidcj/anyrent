/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.repository 
 * @author: caojun 
 * @date: 2018年5月18日 下午2:20:30 
 */
package com.javacj.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.javacj.entity.SubwayStation;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.repository 
 * @author: caojun 
 * @date: 2018年5月18日 下午2:20:30 
 */
public interface SubwayStationRepository extends CrudRepository<SubwayStation, Long>{
	List<SubwayStation> findAllBySubwayId(Long subwayId);
}
