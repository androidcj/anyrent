/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.service.house 
 * @author: caojun 
 * @date: 2018年5月15日 下午11:06:29 
 */
package com.javacj.service.house;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javacj.dto.SubwayDto;
import com.javacj.dto.SubwayStationDto;
import com.javacj.dto.SupportAddressDto;
import com.javacj.entity.SubWay;
import com.javacj.entity.SubwayStation;
import com.javacj.entity.SupportAddress;
import com.javacj.entity.SupportAddress.Level;
import com.javacj.repository.SubwayRepository;
import com.javacj.repository.SubwayStationRepository;
import com.javacj.repository.SupportAddressRepository;
import com.javacj.service.ServiceMultiResult;
import com.javacj.service.ServiceResult;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.service.house 
 * @author: caojun 
 * @date: 2018年5月15日 下午11:06:29 
 */
@Service("addressSupportService")
public class AddressSupportService implements IAddressSupportService{
	
	/* (non-Javadoc)
	 * @see com.javacj.service.house.IAddressSupportService#findAllCities()
	 */
	@Autowired
	private SupportAddressRepository supportAddressRepository;
	@Autowired
	private SubwayRepository subwayRepository;
	
	@Autowired
	private SubwayStationRepository subwayStationRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ServiceMultiResult<SupportAddressDto> findAllCities() {
		// TODO Auto-generated method stub
		List<SupportAddress> addressList= supportAddressRepository.findAllByLevel(SupportAddress.Level.CITY.getValue());
		List<SupportAddressDto> addressDtoList = new ArrayList<SupportAddressDto>();
		for (SupportAddress supportAddress : addressList) {
			SupportAddressDto supportDto = modelMapper.map(supportAddress, SupportAddressDto.class);
			addressDtoList.add(supportDto);
		}
		return new ServiceMultiResult<>(addressDtoList.size(),addressDtoList);
	}

	/* (non-Javadoc)
	 * @see com.javacj.service.house.IAddressSupportService#findRegionsByCity(java.lang.String)
	 */
	@Override
	public ServiceMultiResult<SupportAddressDto> findRegionsByCity(String city) {
		// TODO Auto-generated method stub
		List<SupportAddress> regions = supportAddressRepository.findRegionsByEnNameAndLevel(city,SupportAddress.Level.REGION.getValue());
		List<SupportAddressDto> addressDtoList = new ArrayList<SupportAddressDto>();
		for (SupportAddress supportAddress : regions) {
			SupportAddressDto supportDto = modelMapper.map(supportAddress, SupportAddressDto.class);
			addressDtoList.add(supportDto);
		}
		return new ServiceMultiResult(addressDtoList.size(),addressDtoList);
	}

	/* (non-Javadoc)
	 * @see com.javacj.service.house.IAddressSupportService#findAllSubwayByCity(java.lang.String)
	 */
	@Override
	public ServiceMultiResult<SubwayDto> findAllSubwayByCity(String cityEnName) {
		// TODO Auto-generated method stub
		List<SubWay> list_subway =  subwayRepository.findAllSubwayByCity(cityEnName);
		List<SubwayDto> list_subwaydto = new ArrayList<SubwayDto>();
//		modelMapper
		for (SubWay subWay : list_subway) {
			SubwayDto subdto = modelMapper.map(subWay, SubwayDto.class);
			list_subwaydto.add(subdto);
		}
		return new ServiceMultiResult<SubwayDto>(list_subwaydto.size(),list_subwaydto);
	}

	/* (non-Javadoc)
	 * @see com.javacj.service.house.IAddressSupportService#findAllStationBySubway(java.lang.String)
	 */
	@Override
	public ServiceMultiResult<SubwayStationDto> findAllStationBySubway(
			Long subwayId) {
		// TODO Auto-generated method stub
		List<SubwayStation> list_subway =  subwayRepository.findAllStationBySubway(subwayId);
		List<SubwayStationDto> dtolist= new ArrayList<SubwayStationDto>();
		for (SubwayStation subwayStation : list_subway) {
			SubwayStationDto subwayStationDto = modelMapper.map(subwayStation, SubwayStationDto.class);
			dtolist.add(subwayStationDto);
		}
		
		return new ServiceMultiResult<SubwayStationDto>(dtolist.size(),dtolist);
	}

	/* (non-Javadoc)
	 * @see com.javacj.service.house.IAddressSupportService#findCityAndRegion(java.lang.String, java.lang.String)
	 */
	@Override
	public Map<Level, SupportAddressDto> findCityAndRegion(String cityEnName,
			String regionEnName) {
		// TODO Auto-generated method stub
		Map<SupportAddress.Level, SupportAddressDto> result = new HashMap<>();
		SupportAddress city = supportAddressRepository.findByEnNameAndLevel(cityEnName, SupportAddress.Level.CITY
                .getValue());
		SupportAddress region = supportAddressRepository.findByEnNameAndBelongTo(regionEnName, city.getEnName());
		result.put(SupportAddress.Level.CITY, modelMapper.map(city, SupportAddressDto.class));
        result.put(SupportAddress.Level.REGION, modelMapper.map(region, SupportAddressDto.class));
		return result;
	}

	/* (non-Javadoc)
	 * @see com.javacj.service.house.IAddressSupportService#findSubway(java.lang.Long)
	 */
	@Override
	public ServiceResult<SubwayDto> findSubway(Long subwayId) {
		// TODO Auto-generated method stub
		SubWay subway = subwayRepository.findOne(subwayId);
		if(subway == null){
			return ServiceResult.notFound();
		}
		SubwayDto subdto = modelMapper.map(subway, SubwayDto.class);
		return ServiceResult.of(subdto);
	}

	/* (non-Javadoc)
	 * @see com.javacj.service.house.IAddressSupportService#findSubwayStation(java.lang.Long)
	 */
	@Override
	public ServiceResult<SubwayStationDto> findSubwayStation(
			Long subwayStationId) {
		// TODO Auto-generated method stub
		SubwayStation subwayStation = subwayStationRepository.findOne(subwayStationId);
		if(subwayStation ==null){
			return ServiceResult.notFound();
		}
		SubwayStationDto subwayStationDto = modelMapper.map(subwayStation, SubwayStationDto.class);
		return ServiceResult.of(subwayStationDto);
	}

	/* (non-Javadoc)
	 * @see com.javacj.service.house.IAddressSupportService#findAllRegionsByCityName(java.lang.String)
	 */
	@Override
	public ServiceMultiResult<SupportAddressDto> findAllRegionsByCityName(
			String cityEnName) {
		// TODO Auto-generated method stub
		List<SupportAddress>  addressList =  supportAddressRepository.findAllRegionsByCityName(cityEnName);
		List<SupportAddressDto> addressDtoList = new ArrayList<SupportAddressDto>();
		if(addressList ==null){
			return new ServiceMultiResult(0,null);
		}
		addressList.forEach((address)->{
			SupportAddressDto addressDto = modelMapper.map(address, SupportAddressDto.class);
			addressDtoList.add(addressDto);
		});
		
		return new ServiceMultiResult(addressDtoList.size(),addressDtoList);
	}

	/* (non-Javadoc)
	 * @see com.javacj.service.house.IAddressSupportService#findCity(java.lang.String)
	 */
	@Override
	public ServiceResult<SupportAddressDto> findCity(String cityEnName) {
		// TODO Auto-generated method stub
		
		SupportAddress address = supportAddressRepository.findByEnName(cityEnName);
		if(address== null){
			return ServiceResult.notFound();
		}
		SupportAddressDto supportAddressDto = modelMapper.map(address, SupportAddressDto.class);
		return ServiceResult.of(supportAddressDto);
	}

}
