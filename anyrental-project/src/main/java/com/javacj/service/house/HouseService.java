/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.service.house 
 * @author: caojun 
 * @date: 2018年5月18日 上午11:51:25 
 */
package com.javacj.service.house;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javacj.base.ApiResponse;
import com.javacj.base.HouseSort;
import com.javacj.base.HouseStatus;
import com.javacj.base.LoginUserUtil;
import com.javacj.dto.HouseDTO;
import com.javacj.dto.HouseDetailDTO;
import com.javacj.dto.HousePictureDTO;
import com.javacj.entity.House;
import com.javacj.entity.HouseDetail;
import com.javacj.entity.HousePicture;
import com.javacj.entity.HouseTag;
import com.javacj.entity.SubWay;
import com.javacj.entity.SubwayStation;
import com.javacj.repository.HouseDetailRepository;
import com.javacj.repository.HousePictureRepository;
import com.javacj.repository.HouseRepository;
import com.javacj.repository.HouseTagRepository;
import com.javacj.repository.SubwayRepository;
import com.javacj.repository.SubwayStationRepository;
import com.javacj.service.ServiceMultiResult;
import com.javacj.service.ServiceResult;
import com.javacj.web.form.DatatableSearch;
import com.javacj.web.form.HouseForm;
import com.javacj.web.form.PhotoForm;
import com.javacj.web.form.RentSearch;
import com.qiniu.http.Response;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.service.house 
 * @author: caojun 
 * @date: 2018年5月18日 上午11:51:25 
 */
@Service("houseService")

public class HouseService implements IHouseService{
	@Value("${qiniu.cdn.prefix}")
	private String cdnPrefix;
	
	@Autowired
	private  HouseRepository houseRepository;
	
	@Autowired
	private  HouseDetailRepository houseDetailRepository;
	
	@Autowired
	private  SubwayRepository subwayRepository;
	
	@Autowired
	private SubwayStationRepository subwayStationRepository;
	
	@Autowired
	private HousePictureRepository housePictureRepository;
	
	@Autowired
	private HouseTagRepository houseTagRepository;
	
	@Autowired
	private ModelMapper modelMapper;
    @Autowired
    private IQiuNiuService qiNiuService;
	
//    @Autowired
//    private ISearchService searchService;
    
	@Transactional
	public ServiceResult<HouseDTO> save(HouseForm houseForm){
		HouseDetail detail = new HouseDetail();
		House house = new House();
		modelMapper.map(houseForm, house);
		wrapperDetailInfo(detail,houseForm);
//		if(subwayResult==null){
//			return subwayResult;
//		}
		Date date = new Date();
		house.setCreateTime(date);
		house.setLastUpdateTime(date);
		house.setAdminId(LoginUserUtil.getLoginUserId());
		house = houseRepository.save(house);
		detail.setHouseId(house.getId());
		detail = houseDetailRepository.save(detail);
		List<HousePicture> pictures=  generatePictures(houseForm,house.getId());
		Iterable<HousePicture> housePictures = housePictureRepository.save(pictures);
		HouseDTO houseDTO = modelMapper.map(house, HouseDTO.class);
        HouseDetailDTO houseDetailDTO = modelMapper.map(detail, HouseDetailDTO.class);
        houseDTO.setHouseDetail(houseDetailDTO);
        List<HousePictureDTO> pictureDTOS = new ArrayList<>();
        housePictures.forEach(housePicture -> pictureDTOS.add(modelMapper.map(housePicture, HousePictureDTO.class)));
        houseDTO.setPictures(pictureDTOS);
        houseDTO.setCover(this.cdnPrefix + houseDTO.getCover());
        
        List<String> tags = houseForm.getTags();
        if (tags != null && !tags.isEmpty()) {
            List<HouseTag> houseTags = new ArrayList<>();
            for (String tag : tags) {
                houseTags.add(new HouseTag(house.getId(), tag));
            }
            houseTagRepository.save(houseTags);
            houseDTO.setTags(tags);
        }
		return new ServiceResult<HouseDTO>(true,null,houseDTO);
	};
	
	
	/**
     * 图片对象列表信息填充
     * @param form
     * @param houseId
     * @return
     */
    private List<HousePicture> generatePictures(HouseForm form, Long houseId) {
        List<HousePicture> pictures = new ArrayList<>();
        if (form.getPhotos() == null || form.getPhotos().isEmpty()) {
            return pictures;
        }

        for (PhotoForm photoForm : form.getPhotos()) {
            HousePicture picture = new HousePicture();
            picture.setHouseId(houseId);
            picture.setCdnPrefix(cdnPrefix);
            picture.setPath(photoForm.getPath());
            picture.setWidth(photoForm.getWidth());
            picture.setHeight(photoForm.getHeight());
            pictures.add(picture);
        }
        return pictures;
    }
	
	
	
	 /**
     * 房源详细信息对象填充
     * @param houseDetail
     * @param houseForm
     * @return
     */
    private ServiceResult<HouseDTO> wrapperDetailInfo(HouseDetail houseDetail, HouseForm houseForm) {
        SubWay subway = subwayRepository.findOne(houseForm.getSubwayLineId()==null?0:houseForm.getSubwayLineId());
//        if (subway == null) {
//            return new ServiceResult<>(false, "Not valid subway line!");
//        }
        SubwayStation subwayStation = subwayStationRepository.findOne(houseForm.getSubwayStationId()==null?0:houseForm.getSubwayStationId());
//        if (subwayStation == null || subway.getId() != subwayStation.getSubwayId()) {
//            return new ServiceResult<>(false, "Not valid subway station!");
//        }

        houseDetail.setSubwayLineId(subway.getId()==null?-1:subway.getId());
        houseDetail.setSubwayLineName(subway.getName()==null?"无地铁":subway.getName());

        houseDetail.setSubwayStationId(subwayStation.getId()==null?-1:subwayStation.getId());
        houseDetail.setSubwayStationName(subwayStation.getName()==null?"无地铁":subwayStation.getName());

        houseDetail.setDescription(houseForm.getDescription());
        houseDetail.setDetailAddress(houseForm.getDetailAddress());
        houseDetail.setLayoutDesc(houseForm.getLayoutDesc());
        houseDetail.setRentWay(houseForm.getRentWay());
        houseDetail.setRoundService(houseForm.getRoundService());
        houseDetail.setTraffic(houseForm.getTraffic());
//        housedto.setHouseDetail(houseDetail);
        
        return  null;
    }


	/* (non-Javadoc)
	 * @see com.javacj.service.house.IHouseService#update(com.javacj.web.form.HouseForm)
	 */
	@Override
	@Transactional
	public ServiceResult update(HouseForm houseForm) {
		// TODO Auto-generated method stub
		House house = houseRepository.findOne(houseForm.getId());
		if(house ==null){
			return ServiceResult.notFound();
		}
		
		HouseDetail houseDetail = houseDetailRepository.findByHouseId(house.getId());
		if(houseDetail ==null){
			return ServiceResult.notFound();
		}
		//组装houseDetail数据
		wrapperDetailInfo(houseDetail, houseForm);
		houseDetailRepository.save(houseDetail);
		List<HousePicture> piclist=  generatePictures(houseForm,house.getId());
		housePictureRepository.save(piclist);
		
		if(houseForm.getCover() ==null){
			houseForm.setCover(house.getCover());
		}
		modelMapper.map(houseForm, house);
		house.setLastUpdateTime(new Date());
		houseRepository.save(house);
		return ServiceResult.success();
	}


	/* (non-Javadoc)
	 * @see com.javacj.service.house.IHouseService#adminQuery(com.javacj.web.form.DatatableSearch)
	 */
	@Override
	public ServiceMultiResult<HouseDTO> adminQuery(DatatableSearch searchBody) {
		// TODO Auto-generated method stub
		List<HouseDTO> houseDTOS = new ArrayList<>();
		
		Sort sort=  new Sort(Sort.Direction.fromString(searchBody.getDirection()),searchBody.getOrderBy());
		int page  = searchBody.getStart() / searchBody.getLength();
		Pageable pageable = new PageRequest(page,searchBody.getLength(),sort);
		Specification<House> specification = (root,query,cb)->{
			
			Predicate predicate= cb.equal(root.get("adminId"), LoginUserUtil.getLoginUserId());  //只能看到自己的房源 
			predicate = cb.and(predicate,cb.notEqual(root.get("status"), HouseStatus.DELETED.getValue()));	//筛选删除状态的房源
			
			//如果选择了城市之后
			if(searchBody.getCity() !=null){
				predicate = cb.and(predicate,cb.equal(root.get("cityEnName"), searchBody.getCity()));
			}
			
			//如果选择了状态筛选
			if(searchBody.getStatus() !=null){
				predicate = cb.and(predicate,cb.equal(root.get("status"), searchBody.getStatus()));
			}
			
			//时间筛选
			if(searchBody.getCreateTimeMin() !=null){
				predicate = cb.and(predicate,cb.greaterThanOrEqualTo(root.get("createTime"), searchBody.getCreateTimeMin()));
			}
			if(searchBody.getCreateTimeMax() !=null){
				predicate = cb.and(predicate,cb.lessThanOrEqualTo(root.get("createTime"), searchBody.getCreateTimeMax()));
			}
			
			
			if(searchBody.getTitle() !=null){
				predicate = cb.and(predicate,cb.like(root.get("title"), "%"+searchBody.getTitle()+"%"));
				
				
			}
			return predicate;
			
			
		};
		
		Page<House> ihouse = houseRepository.findAll(specification,pageable);
//		Iterable<House> ihouse = houseRepository.findAll(pageable);
		ihouse.forEach(house ->{
			HouseDTO housedto = modelMapper.map(house, HouseDTO.class);
			housedto.setCover(this.cdnPrefix+house.getCover());
			houseDTOS.add(housedto);
		});
		return new ServiceMultiResult<>(ihouse.getTotalElements(),houseDTOS);
	}


	/* (non-Javadoc)
	 * @see com.javacj.service.house.IHouseService#findCompleteOne(java.lang.Long)
	 */
	@Override
	public ServiceResult<HouseDTO> findCompleteOne(Long id) {
		// TODO Auto-generated method stub
		House house = houseRepository.findOne(id);
		if(house == null){
			return ServiceResult.notFound();
			
		}
		HouseDetail houseDetail = houseDetailRepository.findByHouseId(id);
		List<HousePicture> housePictures = housePictureRepository.findAllByHouseId(id);
		HouseDetailDTO houseDetailDTO = modelMapper.map(houseDetail, HouseDetailDTO.class);
		List<HousePictureDTO> housePictureDTOs = new ArrayList<HousePictureDTO>();
		
		housePictures.forEach((housepic)->{
			HousePictureDTO picdto = modelMapper.map(housepic, HousePictureDTO.class);
			housePictureDTOs.add(picdto);
		});
		
		
		List<HouseTag> tags = houseTagRepository.findAllByHouseId(id); 
		
		
		List<String> taglist= new ArrayList<>();
		tags.forEach((houseTag)->{
			taglist.add(houseTag.getName());
			
		});
		HouseDTO housedto = modelMapper.map(house, HouseDTO.class);
		housedto.setHouseDetail(houseDetailDTO);
		housedto.setPictures(housePictureDTOs);
		housedto.setTags(taglist);
		
		return  ServiceResult.of(housedto);
	}


	/* (non-Javadoc)
	 * @see com.javacj.service.house.IHouseService#removePhoto(java.lang.Long)
	 */
	@Override
	@Transactional
	public ServiceResult removePhoto(Long id) {
		// TODO Auto-generated method stub
	    HousePicture picture = housePictureRepository.findOne(id);
        if (picture == null) {
            return ServiceResult.notFound();
        }

		
		try {
			 Response response =qiNiuService.delete(picture.getPath());
			if(response.isOK()){
				housePictureRepository.delete(id);
				return ServiceResult.success();
			}else {
                return new ServiceResult(false, response.error);
            }
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ServiceResult(false, e.getMessage());
		}
	}


	/* (non-Javadoc)
	 * @see com.javacj.service.house.IHouseService#updateCover(java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional
	public ServiceResult updateCover(Long coverId, Long targetId) {
		// TODO Auto-generated method stub
		
		 HousePicture cover = housePictureRepository.findOne(coverId);
	        if (cover == null) {
	            return ServiceResult.notFound();
	        }
	        houseRepository.updateCover(targetId, cover.getPath());
	        return ServiceResult.success();
	}


	/* (non-Javadoc)
	 * @see com.javacj.service.house.IHouseService#addTag(java.lang.Long, java.lang.String)
	 */
	@Override
	@Transactional
	public ServiceResult addTag(Long houseId, String tag) {
		// TODO Auto-generated method stub
		
		House house = houseRepository.findOne(houseId);
		if (house == null) {
            return ServiceResult.notFound();
        }
		HouseTag houseTag = houseTagRepository.findByNameAndHouseId(tag, houseId);
		if (houseTag != null) {
            return new ServiceResult(false, "标签已存在");
        }
		houseTagRepository.save(new HouseTag(houseId,tag));
		return ServiceResult.success();
	}


	/* (non-Javadoc)
	 * @see com.javacj.service.house.IHouseService#removeTag(java.lang.Long, java.lang.String)
	 */
	@Override
	public ServiceResult removeTag(Long houseId, String tag) {
		// TODO Auto-generated method stub
		House house = houseRepository.findOne(houseId);
        if (house == null) {
            return ServiceResult.notFound();
        }
		HouseTag housetag = houseTagRepository.findByNameAndHouseId(tag, houseId);
		if(housetag!=null){
			houseTagRepository.delete(housetag);
		}
		return ServiceResult.success();
	}


	/* (non-Javadoc)
	 * @see com.javacj.service.house.IHouseService#updateStatus(java.lang.Long, int)
	 */
	@Override
	public ServiceResult updateStatus(Long id, int status) {
		// TODO Auto-generated method stub
		House house = houseRepository.findOne(id);
		if(house == null){
			//没找到房源
			return ServiceResult.notFound();
		}
		 if (house.getStatus() == status) {
	            return new ServiceResult(false, "状态没有发生变化");
	        }

	        if (house.getStatus() == HouseStatus.RENTED.getValue()) {
	            return new ServiceResult(false, "已出租的房源不允许修改状态");
	        }

	        if (house.getStatus() == HouseStatus.DELETED.getValue()) {
	            return new ServiceResult(false, "已删除的资源不允许操作");
	        }
		houseRepository.updateStatusById(id,status);
		return ServiceResult.success();
	}


	/* (non-Javadoc)
	 * @see com.javacj.service.house.IHouseService#query(com.javacj.web.form.RentSearch)
	 */
	@Override
	public ServiceMultiResult<HouseDTO> query(RentSearch rentSearch) {
//		Sort sort = new Sort(Sort.Direction.DESC,"lastUpdateTime");
		
		Sort sort = HouseSort.generateSort(rentSearch.getOrderBy(), rentSearch.getOrderDirection());
		int page = rentSearch.getStart()/rentSearch.getSize();  //获取是第几页
		//得到分页器
		Pageable pageable = new PageRequest(page,rentSearch.getSize(),sort);
		Specification<House> specification = (root,query,cb)->{
			Predicate predicate = cb.equal(root.get("status"), HouseStatus.PASS.getValue());
			predicate = cb.and(predicate,cb.equal(root.get("cityEnName"),rentSearch.getCityEnName()));
			if(HouseSort.DISTANCE_TO_SUBWAY_KEY.equals(rentSearch.getOrderBy())){
				predicate = cb.and(predicate,cb.gt(root.get(HouseSort.DISTANCE_TO_SUBWAY_KEY), -1));
			}
			return predicate;
		};
		List<HouseDTO> houseDtoList = new ArrayList<HouseDTO>();
		Page<House> housePage   = houseRepository.findAll(specification, pageable);
		housePage.forEach((house)->{
			HouseDTO houseDto = modelMapper.map(house, HouseDTO.class);
			HouseDetail houseDetail = houseDetailRepository.findByHouseId(house.getId());
			HouseDetailDTO houseDetailDTO = modelMapper.map(houseDetail, HouseDetailDTO.class);
			houseDto.setHouseDetail(houseDetailDTO);
			List<String> tagsname = new ArrayList<String>();
			List<HouseTag> listtags = houseTagRepository.findAllByHouseId(house.getId());
			listtags.forEach((tag)->{
				tagsname.add(tag.getName());
			});
			houseDto.setTags(tagsname);
			houseDto.setCover(this.cdnPrefix+house.getCover());
			houseDtoList.add(houseDto);
		});
		
		
		
		
		return new ServiceMultiResult(housePage.getTotalElements(),houseDtoList);
		// TODO Auto-generated method stub
		
//		if (rentSearch.getKeywords() != null && !rentSearch.getKeywords().isEmpty()) {
//            ServiceMultiResult<Long> serviceResult = searchService.query(rentSearch);
//            if (serviceResult.getTotal() == 0) {
//                return new ServiceMultiResult<>(0, new ArrayList<>());
//            }
//
//            return new ServiceMultiResult<>(serviceResult.getTotal(), wrapperHouseResult(serviceResult.getResult()));
//        }

//		return null;
	}
	


    private List<HouseDTO> wrapperHouseResult(List<Long> houseIds) {
        List<HouseDTO> result = new ArrayList<>();

        Map<Long, HouseDTO> idToHouseMap = new HashMap<>();
        Iterable<House> houses = houseRepository.findAll(houseIds);
        houses.forEach(house -> {
            HouseDTO houseDTO = modelMapper.map(house, HouseDTO.class);
            houseDTO.setCover(this.cdnPrefix + house.getCover());
            idToHouseMap.put(house.getId(), houseDTO);
        });

        wrapperHouseList(houseIds, idToHouseMap);

        // 矫正顺序
        for (Long houseId : houseIds) {
            result.add(idToHouseMap.get(houseId));
        }
        return result;
    }
	
	
    /**
     * 渲染详细信息 及 标签
     * @param houseIds
     * @param idToHouseMap
     */
    private void wrapperHouseList(List<Long> houseIds, Map<Long, HouseDTO> idToHouseMap) {
        List<HouseDetail> details = houseDetailRepository.findAllByHouseIdIn(houseIds);
        details.forEach(houseDetail -> {
            HouseDTO houseDTO = idToHouseMap.get(houseDetail.getHouseId());
            HouseDetailDTO detailDTO = modelMapper.map(houseDetail, HouseDetailDTO.class);
            houseDTO.setHouseDetail(detailDTO);
        });

        List<HouseTag> houseTags = houseTagRepository.findAllByHouseIdIn(houseIds);
        houseTags.forEach(houseTag -> {
            HouseDTO house = idToHouseMap.get(houseTag.getHouseId());
            house.getTags().add(houseTag.getName());
        });
    }
}
