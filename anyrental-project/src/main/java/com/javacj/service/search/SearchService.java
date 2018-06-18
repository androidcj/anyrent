/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.service.search 
 * @author: caojun 
 * @date: 2018年6月17日 下午10:01:26 
 */
package com.javacj.service.search;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javacj.entity.House;
import com.javacj.repository.HouseRepository;
import com.javacj.service.house.HouseService;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.service.search 
 * @author: caojun 
 * @date: 2018年6月17日 下午10:01:26 
 */
@Service("searchService")
public class SearchService implements ISearchService{
	Logger logger =  LoggerFactory.getLogger(ISearchService.class);
	
	@Autowired
	public HouseRepository houseRepository;
	
	@Autowired
	public ModelMapper modelMapper;
	
	/* (non-Javadoc)
	 * @see com.javacj.service.search.ISearchService#index(java.lang.Long)
	 */
	@Override
	public void index(Long houseId) {
		// TODO Auto-generated method stub
		House house = houseRepository.findOne(houseId);
		if(house == null){   //如果在数据库中找不到
			logger.error("not find");
		}
		HouseIndexTemplate indexTemplate = new HouseIndexTemplate();
		
	}

	/* (non-Javadoc)
	 * @see com.javacj.service.search.ISearchService#remove(java.lang.Long)
	 */
	@Override
	public void remove(Long houseId) {
		// TODO Auto-generated method stub
		
	}
	
}
