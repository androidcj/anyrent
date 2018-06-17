/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.web.controller.house 
 * @author: caojun 
 * @date: 2018年5月15日 下午10:27:21 
 */
package com.javacj.web.controller.house;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javacj.base.ApiResponse;
import com.javacj.base.RentValueBlock;
import com.javacj.dto.HouseDTO;
import com.javacj.dto.HouseDetailDTO;
import com.javacj.dto.SubwayDto;
import com.javacj.dto.SubwayStationDto;
import com.javacj.dto.SupportAddressDto;
import com.javacj.dto.UserDTO;
import com.javacj.entity.SupportAddress;
import com.javacj.repository.HouseRepository;
import com.javacj.service.ServiceMultiResult;
import com.javacj.service.ServiceResult;
import com.javacj.service.house.IAddressSupportService;
import com.javacj.service.house.IHouseService;
import com.javacj.service.user.IUserService;
import com.javacj.web.form.RentSearch;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.web.controller.house 
 * @author: caojun 
 * @date: 2018年5月15日 下午10:27:21 
 */

@Controller
public class HouseController {
	@Autowired
	private IAddressSupportService addressSupportService;
	
	@Autowired
	private IHouseService houseService;
	
	@Autowired
	private IUserService userService;
	
	@GetMapping("address/support/cities")
	@ResponseBody
	public ApiResponse getSupportCities() {
		//获取所有城市
		ServiceMultiResult<SupportAddressDto> serviceMultiResult = addressSupportService.findAllCities();
		if(serviceMultiResult.getResultSize()==0){
			return ApiResponse.ofSuccess(ApiResponse.Status.NOT_FOUND);
			
		}
		return ApiResponse.ofSuccess(serviceMultiResult.getResult());
	}
	
	@GetMapping("/address/support/regions")
	@ResponseBody
	public ApiResponse getSupportRegions(@RequestParam("city_name")String city_name) {
		ServiceMultiResult<SupportAddressDto> result = addressSupportService.findRegionsByCity(city_name);
		if(result.getResultSize()==0){
			return ApiResponse.ofSuccess(ApiResponse.Status.NOT_FOUND);
		}
		return ApiResponse.ofSuccess(result.getResult());
	}
	
	
	/**
     * 获取具体城市所支持的地铁线路
     * @param cityEnName
     * @return
     */
    @GetMapping("/address/support/subway/line")
    @ResponseBody
    public ApiResponse getSupportSubwayLine(@RequestParam(name = "city_name") String cityEnName) {
    	ServiceMultiResult<SubwayDto> result = addressSupportService.findAllSubwayByCity(cityEnName);
    	if(result.getResultSize()==0){
    		return ApiResponse.ofSuccess(ApiResponse.Status.NOT_FOUND);
    	}
    	 return ApiResponse.ofSuccess(result.getResult());
    }
	
    
    
    /**
     * 获取对应地铁线路所支持的地铁站点
     * @param subwayId
     * @return
     */
    @GetMapping("/address/support/subway/station")
    @ResponseBody
    public ApiResponse getSupportSubwayStation(@RequestParam(name = "subway_id") Long subwayId) {
    	ServiceMultiResult<SubwayStationDto> result = addressSupportService.findAllStationBySubway(subwayId);
        if (result.getResultSize()==0) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_FOUND);
        }
        return ApiResponse.ofSuccess(result.getResult());
    }
    
    
    //跳转到租房页面
    @GetMapping("rent/house")
    public String rentHousePage(@ModelAttribute RentSearch rentSearch
    							,Model model,HttpSession session,
    							RedirectAttributes redirectAttributes){
    	if(rentSearch.getCityEnName() ==null){
    		String cityEnNameInSession = (String) session.getAttribute("cityEnName");
    		if (cityEnNameInSession == null) {
                redirectAttributes.addAttribute("msg", "must_chose_city");
                return "redirect:/index";
            } else {
                rentSearch.setCityEnName(cityEnNameInSession);
            }
    	}else{
    		session.setAttribute("cityEnName", rentSearch.getCityEnName());
    		
    	}
    	ServiceResult<SupportAddressDto> city = addressSupportService.findCity(rentSearch.getCityEnName());
        if (!city.isSuccess()) {
            redirectAttributes.addAttribute("msg", "must_chose_city");
            return "redirect:/index";
        }
        model.addAttribute("currentCity", city.getResult());
        
        ServiceMultiResult<SupportAddressDto> addressResult = addressSupportService.findAllRegionsByCityName(rentSearch.getCityEnName());
        if (addressResult.getResult() == null || addressResult.getTotal() < 1) {
            redirectAttributes.addAttribute("msg", "must_chose_city");
            return "redirect:/index";
        }
        
        
        
//    	ServiceMultiResult<SupportAddressDto> addressResult = addressSupportService.findAllRegionsByCityName(rentSearch.getCityEnName());
    	
        ServiceMultiResult<HouseDTO> serviceMultiResult = houseService.query(rentSearch);

        model.addAttribute("total", serviceMultiResult==null?0:serviceMultiResult.getTotal());
        List<HouseDTO> houseDtoList =  serviceMultiResult.getResult();
        houseDtoList.forEach((housedto)->{
        	HouseDetailDTO detailDto = housedto.getHouseDetail();
        	System.out.println("detailDto=="+detailDto.getSubwayLineName());
        });
        
        model.addAttribute("houses", serviceMultiResult == null ?new ArrayList():serviceMultiResult.getResult());

        if (rentSearch.getRegionEnName() == null) {
            rentSearch.setRegionEnName("*");
        }

        model.addAttribute("searchBody", rentSearch);
        model.addAttribute("regions", addressResult.getResult());

        model.addAttribute("priceBlocks", RentValueBlock.PRICE_BLOCK);
        model.addAttribute("areaBlocks", RentValueBlock.AREA_BLOCK);

        model.addAttribute("currentPriceBlock", RentValueBlock.matchPrice(rentSearch.getPriceBlock()));
        model.addAttribute("currentAreaBlock", RentValueBlock.matchArea(rentSearch.getAreaBlock()));

        return "rent-list";
    	
    	
    }
    
    @GetMapping("rent/house/show/{id}")
    public String show(@PathVariable(value = "id") Long houseId,
                       Model model) {
    	if (houseId <= 0) {
            return "404";
        }
    	ServiceResult<HouseDTO> serviceResult = houseService.findCompleteOne(houseId);
    	if(!serviceResult.isSuccess()){
    		return "404";
    	}
    	
    	HouseDTO houseDTO = serviceResult.getResult();
    	Map<SupportAddress.Level, SupportAddressDto> addressMap = addressSupportService.findCityAndRegion(houseDTO.getCityEnName(), houseDTO.getRegionEnName());
    	SupportAddressDto city = addressMap.get(SupportAddress.Level.CITY);
    	SupportAddressDto region = addressMap.get(SupportAddress.Level.REGION);
    	model.addAttribute("city", city);
        model.addAttribute("region", region);
    	
        ServiceResult<UserDTO> userDTOServiceResult = userService.findById(houseDTO.getAdminId());
        model.addAttribute("agent", userDTOServiceResult.getResult());
        model.addAttribute("house", houseDTO);
        model.addAttribute("houseCountInDistrict", 0);
    	return "house-detail";
    	
    }
}
