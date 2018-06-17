/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.admin 
 * @author: caojun 
 * @date: 2018年5月6日 下午12:36:36 
 */
package com.javacj.admin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.kafka.common.protocol.ApiKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.google.gson.Gson;
import com.javacj.base.ApiDataTableResponse;
import com.javacj.base.ApiResponse;
import com.javacj.base.HouseOperation;
import com.javacj.base.HouseStatus;
import com.javacj.dto.HouseDTO;
import com.javacj.dto.HouseDetailDTO;
import com.javacj.dto.SubwayDto;
import com.javacj.dto.SubwayStationDto;
import com.javacj.dto.SupportAddressDto;
import com.javacj.entity.SupportAddress;
import com.javacj.service.ServiceMultiResult;
import com.javacj.service.ServiceResult;
import com.javacj.service.house.IAddressSupportService;
import com.javacj.service.house.IHouseService;
import com.javacj.service.house.IQiuNiuService;
import com.javacj.web.dto.QiNiuPutRet;
import com.javacj.web.form.DatatableSearch;
import com.javacj.web.form.HouseForm;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.admin 
 * @author: caojun 
 * @date: 2018年5月6日 下午12:36:36 
 */

@Controller
public class AdminController {
	@Autowired
	private IQiuNiuService qiniuService;
	
	@Autowired
	private IAddressSupportService addressSupportService;
	@Autowired
	private IHouseService houseService;
	
	@Autowired
	private Gson gson;
	
	@GetMapping(value="/admin/center")
	public String adminCenterPage(){
		return "admin/center";
	}
	
	@GetMapping(value="/admin/welcome")
	public String welcomePage(){
		return "admin/welcome";
	}
	@RequestMapping(value="/admin/login",method=RequestMethod.GET)
	public String adminLoginPage(){
		return "admin/login";
	}
	@RequestMapping(value="/admin/add/house",method=RequestMethod.GET)
	public String adminAddHousePage(){
		return "admin/house-add";
	}
	
	@PostMapping(value="/admin/upload/photo",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ApiResponse uploadPhoto(@RequestParam("file") MultipartFile file,HttpServletRequest request){
		if(file.isEmpty()){
			return ApiResponse.ofStatus(ApiResponse.Status.NOT_VALID_PARAM);
		}
		
		/**
		 * 七牛云上传
		 */
		try {
			InputStream is = file.getInputStream();
			Response response = qiniuService.uploadFile(is);
			if(response.isOK()){
				QiNiuPutRet ret = gson.fromJson(response.bodyString(), QiNiuPutRet.class);
				return ApiResponse.ofSuccess(ret);
			}else{
				
				return ApiResponse.ofMessage(response.statusCode, response.getInfo());
			}
		} catch (QiniuException e) {
			// TODO: handle exception
			Response response= e.response;
			try {
				return ApiResponse.ofMessage(response.statusCode, response.bodyString());
			} catch (QiniuException e1) {
				// TODO Auto-generated catch block
				return ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVER_ERROR);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVER_ERROR);
		}
	}
	
	

    
    /**
     * 新增房源接口
     * @param houseForm
     * @param bindingResult
     * @return
     */
    @PostMapping("admin/add/house")
    @ResponseBody
    public ApiResponse addHouse(@Valid @ModelAttribute("form-house-add") HouseForm houseForm, BindingResult bindingResult) {
    	if(bindingResult.hasErrors()){
    		return new ApiResponse(HttpStatus.BAD_REQUEST.value(),bindingResult.getAllErrors().get(0).getDefaultMessage(),null);
    	}
    	if(houseForm.getPhotos()==null || houseForm.getCover()==null){
    		return ApiResponse.ofMessage(HttpStatus.BAD_REQUEST.value(), "必须上传图片");
    		
    	}
    	Map<SupportAddress.Level, SupportAddressDto> addressMap = addressSupportService.findCityAndRegion(houseForm.getCityEnName(), houseForm.getRegionEnName());
    	if (addressMap.keySet().size() != 2) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_VALID_PARAM);
        }
    	ServiceResult<HouseDTO> result = houseService.save(houseForm);
        if (result.isSuccess()) {
            return ApiResponse.ofSuccess(result.getResult());
        }
        
        
        return ApiResponse.ofSuccess(ApiResponse.Status.NOT_VALID_PARAM);
    	
    }
    
    /**
     * 房源列表页
     * @return
     */
    @GetMapping("admin/house/list")
    public String houseListPage() {
        return "admin/house-list";
    }
    
    @PostMapping("admin/houses")
    @ResponseBody
    public ApiDataTableResponse houses(@ModelAttribute DatatableSearch datatableSearch){
    	ServiceMultiResult<HouseDTO> muiltihouse =  houseService.adminQuery(datatableSearch);
    	ApiDataTableResponse response = new ApiDataTableResponse(ApiResponse.Status.SUCCESS);
    	response.setData(muiltihouse.getResult());
    	response.setRecordsFiltered(muiltihouse.getTotal());
    	response.setRecordsTotal(muiltihouse.getTotal());
    	response.setDraw(datatableSearch.getDrew());
		return response;
    }
    /**
     * 房源信息编辑页
     * @return
     */
    @GetMapping("admin/house/edit")
    public String houseEditPage(@RequestParam(value = "id") Long id, Model model) {
    	
	   if (id == null || id < 1) {
           return "404";
       }
    	
	   ServiceResult<HouseDTO> result = houseService.findCompleteOne(id);
	   
	   if(!result.isSuccess()){
		   return "404";
	   }
	   
	   HouseDTO houseDto = result.getResult();
	   model.addAttribute("house", houseDto);
	   
	   Map<SupportAddress.Level,SupportAddressDto> addressMap = addressSupportService.findCityAndRegion(houseDto.getCityEnName(), houseDto.getRegionEnName());
	   
	   
	   model.addAttribute("city", addressMap.get(SupportAddress.Level.CITY));
	   model.addAttribute("region", addressMap.get(SupportAddress.Level.REGION));
	   HouseDetailDTO houseDetailDTO = houseDto.getHouseDetail();
	   ServiceResult<SubwayDto> subwayResult = addressSupportService.findSubway(houseDetailDTO.getSubwayLineId());
	   if(subwayResult.isSuccess()){
		   model.addAttribute("subway", subwayResult.getResult());
	   }
			   
	   ServiceResult<SubwayStationDto> subwayStationResult = addressSupportService.findSubwayStation(houseDetailDTO.getSubwayStationId());
	   if(subwayStationResult.isSuccess()){
		   
		   model.addAttribute("station", subwayStationResult.getResult());
	   }
	   return "admin/house-edit";
    }
    
    /**
     * 房源信息编辑页
     * @return
     */
    @PostMapping("admin/house/edit")
    @ResponseBody
    public ApiResponse saveHouse(@Valid @ModelAttribute("form-house-edit") HouseForm houseForm,BindingResult bindingResult){
    	
    	if (bindingResult.hasErrors()) {
            return new ApiResponse(HttpStatus.BAD_REQUEST.value(), bindingResult.getAllErrors().get(0).getDefaultMessage(), null);
        }
    	
    	Map<SupportAddress.Level,SupportAddressDto>map = addressSupportService.findCityAndRegion(houseForm.getCityEnName(), houseForm.getRegionEnName());
    	
    	if(map.keySet().size()!=2){
    		return ApiResponse.ofMessage(HttpStatus.BAD_REQUEST.value(), ApiResponse.Status.NOT_VALID_PARAM.getStandardMessage());
    	}
    	
    	ServiceResult result = houseService.update(houseForm);
    	if(result.isSuccess()){
    		return ApiResponse.ofSuccess(null);
    	}
    	
		return new ApiResponse(ApiResponse.Status.BAD_REQUEST.getCode(), result.getMessage(), null);
    }
    
    
    /**
     * 删除图片
     */
    @DeleteMapping("admin/house/photo")
    @ResponseBody
    public ApiResponse removeHousePhoto(@RequestParam(value = "id") Long id) {
    	ServiceResult result = houseService.removePhoto(id);
    	 if (result.isSuccess()) {
             return ApiResponse.ofStatus(ApiResponse.Status.SUCCESS);
         } else {
             return ApiResponse.ofMessage(HttpStatus.BAD_REQUEST.value(), result.getMessage());
         }
    }
    
    /**
     * 修改封面接口
     * @param coverId
     * @param targetId
     * @return
     */
    @PostMapping("admin/house/cover")
    @ResponseBody
    public ApiResponse updateCover(@RequestParam(value = "cover_id") Long coverId,
                                   @RequestParam(value = "target_id") Long targetId) {
        ServiceResult result = houseService.updateCover(coverId, targetId);

        if (result.isSuccess()) {
            return ApiResponse.ofStatus(ApiResponse.Status.SUCCESS);
        } else {
            return ApiResponse.ofMessage(HttpStatus.BAD_REQUEST.value(), result.getMessage());
        }
    }  
    
    
    /**
     * 增加标签接口
     * @param houseId
     * @param tag
     * @return
     */
    @PostMapping("admin/house/tag")
    @ResponseBody
    public ApiResponse addHouseTag(@RequestParam(value = "house_id") Long houseId,
            @RequestParam(value = "tag") String tag) {
    	 if (houseId < 1 || StringUtils.isEmpty(tag)) {
             return ApiResponse.ofStatus(ApiResponse.Status.BAD_REQUEST);
         }

         ServiceResult result = houseService.addTag(houseId, tag);
         if (result.isSuccess()) {
             return ApiResponse.ofStatus(ApiResponse.Status.SUCCESS);
         } else {
             return ApiResponse.ofMessage(HttpStatus.BAD_REQUEST.value(), result.getMessage());
         }
    }
    
    
    /**
     * 移除标签接口
     * @param houseId
     * @param tag
     * @return
     */
    @DeleteMapping("admin/house/tag")
    @ResponseBody
    public ApiResponse removeHouseTag(@RequestParam(value = "house_id") Long houseId,
                                      @RequestParam(value = "tag") String tag) {
        if (houseId < 1 || StringUtils.isEmpty(tag)) {
            return ApiResponse.ofStatus(ApiResponse.Status.BAD_REQUEST);
        }

        ServiceResult result = houseService.removeTag(houseId, tag);
        if (result.isSuccess()) {
            return ApiResponse.ofStatus(ApiResponse.Status.SUCCESS);
        } else {
            return ApiResponse.ofMessage(HttpStatus.BAD_REQUEST.value(), result.getMessage());
        }
    }
    
    @PutMapping("admin/house/operate/{id}/{operation}")
    @ResponseBody
    public ApiResponse operateHouse(@PathVariable("id") Long id,@PathVariable("operation") int operation){
    	if (id <= 0) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_VALID_PARAM);
        }
        ServiceResult result;
    	switch(operation){
    		case HouseOperation.PASS:
    			result = houseService.updateStatus(id,HouseStatus.PASS.getValue());
    			break;
    		case HouseOperation.PULL_OUT:
    			 result = this.houseService.updateStatus(id, HouseStatus.NOT_AUDITED.getValue());
                 break;
                 
    		case HouseOperation.DELETE:     
    			 result = this.houseService.updateStatus(id, HouseStatus.DELETED.getValue());
                 break;
    		 case HouseOperation.RENT:
                 result = this.houseService.updateStatus(id, HouseStatus.RENTED.getValue());
                 break;
             default:
                 return ApiResponse.ofStatus(ApiResponse.Status.BAD_REQUEST);     
                 
                 
    	}
        
    	 if (result.isSuccess()) {
             return ApiResponse.ofSuccess(null);
         }
         return ApiResponse.ofMessage(HttpStatus.BAD_REQUEST.value(),
                 result.getMessage());
    }
    
}
