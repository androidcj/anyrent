/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.service.house 
 * @author: caojun 
 * @date: 2018年5月15日 下午10:54:20 
 */
package com.javacj.service.house;
import java.util.List;
import java.util.Map;

import com.javacj.dto.SubwayDto;
import com.javacj.dto.SubwayStationDto;
import com.javacj.dto.SupportAddressDto;
import com.javacj.entity.SupportAddress;
import com.javacj.service.ServiceMultiResult;
import com.javacj.service.ServiceResult;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.service.house 
 * @author: caojun 
 * @date: 2018年5月15日 下午10:54:20 
 */
public interface IAddressSupportService {
	 /**
     * 获取所有支持的城市列表
     * @return
     */
	ServiceMultiResult<SupportAddressDto>  findAllCities();
	

    /**
     * 根据英文简写获取具体区域的信息
     * @param cityEnName
     * @param regionEnName
     * @return
     */
    Map<SupportAddress.Level, SupportAddressDto> findCityAndRegion(String cityEnName, String regionEnName);
	
	 /**
     * 根据城市英文简写获取该城市所有支持的区域信息
     * @param cityName
     * @return
     */
	ServiceMultiResult<SupportAddressDto> findRegionsByCity(String city);
    /**
     * 获取该城市所有的地铁线路
     * @param cityEnName
     * @return
     */
	ServiceMultiResult<SubwayDto> findAllSubwayByCity(String cityEnName);
	
	/**
     * 获取该城市所有的地铁站
     * @param subwayId
     * @return
     */
	ServiceMultiResult<SubwayStationDto> findAllStationBySubway(Long subwayId);
	
	/**
	 * 
	 * 获取地铁路线
     * @param subwayId
     * @return
	 */
	
	ServiceResult<SubwayDto> findSubway(Long subwayId);
	
	
	/**
	 * 
	 * 获取地铁站
     * @param subwayStationId
     * @return
	 */
	
	ServiceResult<SubwayStationDto> findSubwayStation(Long subwayStationId);
	
	ServiceMultiResult<SupportAddressDto> findAllRegionsByCityName(String cityEnName);
	
	/**
	 * 通过城市英文名找到城市
	 */
	ServiceResult<SupportAddressDto> findCity(String cityEnName);
}
