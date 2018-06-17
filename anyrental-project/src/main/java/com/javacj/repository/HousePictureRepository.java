/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.repository 
 * @author: caojun 
 * @date: 2018年5月18日 上午11:01:09 
 */
package com.javacj.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.javacj.entity.HousePicture;


public interface HousePictureRepository extends CrudRepository<HousePicture, Long>{
	public List<HousePicture> findAllByHouseId(Long houseId);
}
