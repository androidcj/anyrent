/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.service.search 
 * @author: caojun 
 * @date: 2018年6月17日 下午9:59:17 
 */
package com.javacj.service.search;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * ES接口
 * @Package: com.javacj.service.search 
 * @author: caojun 
 * @date: 2018年6月17日 下午9:59:17 
 */
public interface ISearchService {
	//添加房源
	public void index(Long houseId);
	
	//移除房源
	public void remove(Long houseId);
	
	
}
