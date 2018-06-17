/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.repository 
 * @author: caojun 
 * @date: 2018年5月18日 上午10:53:18 
 */
package com.javacj.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.javacj.entity.House;


public interface HouseRepository extends PagingAndSortingRepository<House, Long>,JpaSpecificationExecutor<House>{
	@Modifying
	@Query("update House as house set house.cover= :cover where house.id = :id")
	public void updateCover(@Param(value = "id") Long id, @Param(value = "cover") String cover);
	
	@Modifying
	@Query("update House as house set house.status= :status where id = :id")
	public void updateStatusById(@Param("id") Long id,@Param("status") int status);
	
	
}
