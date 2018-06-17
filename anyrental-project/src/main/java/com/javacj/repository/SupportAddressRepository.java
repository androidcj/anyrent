/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.repository 
 * @author: caojun 
 * @date: 2018年5月15日 下午10:46:28 
 */
package com.javacj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.javacj.entity.SupportAddress;

public interface SupportAddressRepository extends CrudRepository<SupportAddress, Long>{
	List<SupportAddress> findAllByLevel(String level);   //获取所有对应级别的城市
	@Query("select o from SupportAddress o where belongTo=?1 and level=?2")
	List<SupportAddress>findRegionsByEnNameAndLevel(@Param("city") String city,@Param("level") String level);
	
	 SupportAddress findByEnNameAndLevel(String enName, String level);

	 SupportAddress findByEnNameAndBelongTo(String enName, String belongTo);
	 
	 @Query("select o from SupportAddress o where o.belongTo = :cityEnName")
	 List<SupportAddress>findAllRegionsByCityName(@Param("cityEnName")String cityEnName);
	 
	 @Query("select o from SupportAddress o where o.enName = :cityEnName")
	 SupportAddress findByEnName(@Param("cityEnName") String cityEnName);
	 
	 
}
