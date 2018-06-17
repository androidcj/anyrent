/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.base 
 * @author: caojun 
 * @date: 2018年6月16日 下午4:35:56 
 */
package com.javacj.base;

import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.google.common.collect.Sets;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 排序对象
 * @Package: com.javacj.base 
 * @author: caojun 
 * @date: 2018年6月16日 下午4:35:56 
 */
public class HouseSort {
	
    public static final String DEFAULT_SORT_KEY = "lastUpdateTime";
    public static final String DISTANCE_TO_SUBWAY_KEY = "distanceToSubway";
	
    
    private static final Set<String> SORT_KEYS = Sets.newHashSet(
    		DEFAULT_SORT_KEY,
    		"createTime",
            "price",
            "area",
    		DISTANCE_TO_SUBWAY_KEY
    		);
    		
    public static Sort generateSort(String key, String directionKey) {
    	if(!SORT_KEYS.contains(key)){
    		key = DEFAULT_SORT_KEY;
    	}
    	Direction direct =  Sort.Direction.fromStringOrNull(directionKey);
    	if(direct == null){
    		direct = Sort.Direction.DESC;
    	}
    	Sort sort = new Sort(direct,key);
    	return sort;
    }
    
    
    
}
