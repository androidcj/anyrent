/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.service.house 
 * @author: caojun 
 * @date: 2018年5月18日 上午11:08:31 
 */
package com.javacj.service.house;

import com.javacj.dto.HouseDTO;
import com.javacj.service.ServiceMultiResult;
import com.javacj.service.ServiceResult;
import com.javacj.web.form.DatatableSearch;
import com.javacj.web.form.HouseForm;
import com.javacj.web.form.RentSearch;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 房屋接口
 * @Package: com.javacj.service.house 
 * @author: caojun 
 * @date: 2018年5月18日 上午11:08:31 
 */

public interface IHouseService {
	ServiceResult<HouseDTO> save(HouseForm houseForm);
	ServiceResult update(HouseForm houseForm);
	ServiceMultiResult<HouseDTO> adminQuery(DatatableSearch searchBody);
	//通过 houseId查询  房屋信息
	ServiceResult<HouseDTO> findCompleteOne(Long id);
	
	//删除图片
	ServiceResult removePhoto(Long id);
	
	//更新封面
	ServiceResult updateCover(Long coverId, Long targetId);
	
	//新增tag
	ServiceResult addTag(Long houseId, String tag);
	
	
	//删除tag
	ServiceResult removeTag(Long houseId, String tag);
	
	ServiceResult updateStatus(Long id,int status);
	
	ServiceMultiResult<HouseDTO> query(RentSearch rentSearch);
	
}
