/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.service.search 
 * @author: caojun 
 * @date: 2018年6月17日 下午10:01:26 
 */
package com.javacj.service.search;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.QueryFetchSearchResult;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacj.entity.House;
import com.javacj.entity.HouseDetail;
import com.javacj.entity.HouseTag;
import com.javacj.repository.HouseDetailRepository;
import com.javacj.repository.HouseRepository;
import com.javacj.repository.HouseTagRepository;
import com.javacj.repository.SupportAddressRepository;
import com.javacj.service.house.HouseService;
import com.javacj.service.house.IAddressSupportService;

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
	
	private static final String INDEX_NAME = "house_ryz";
	private static final String INDEX_TYPE = "house";
    private static final String INDEX_TOPIC = "house_build";
	
	
	
	@Autowired
	public HouseRepository houseRepository;
	

    @Autowired
    private HouseDetailRepository houseDetailRepository;

    @Autowired
    private HouseTagRepository tagRepository;

    @Autowired
    private SupportAddressRepository supportAddressRepository;

    @Autowired
    private IAddressSupportService addressService;
	
	
	@Autowired
	public ModelMapper modelMapper;
	
	@Autowired
	public TransportClient esClient;
	
	@Autowired
	public ObjectMapper objectMapper;
	
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
		modelMapper.map(house, indexTemplate);
		
		HouseDetail houseDetail =  houseDetailRepository.findByHouseId(houseId);
		if(houseDetail ==null){
			logger.debug("houseDetail为空");
		}
		modelMapper.map(houseDetail, indexTemplate);
		
		List<HouseTag> housetags = tagRepository.findAllByHouseId(houseId);
		if(housetags!=null && !housetags.isEmpty()){
			List<String> tagNames = new ArrayList<String>();
			housetags.forEach(tags -> tagNames.add(tags.getName()));
			indexTemplate.setTags(tagNames);
		}
		SearchRequestBuilder searchBuilder =  this.esClient.prepareSearch(INDEX_NAME).setTypes(INDEX_TYPE).setQuery(QueryBuilders.termQuery(HouseIndexKey.HOUSE_ID, houseId));
		SearchResponse searchResponse = searchBuilder.get();
		boolean success;
		long totalHit = searchResponse.getHits().getTotalHits();
		if(totalHit == 0){
			success = create(indexTemplate);
		}else if(totalHit == 1){
			SearchHit sh =  searchResponse.getHits().getAt(0);
			String searchId = sh.getId();
			success = update(searchId,indexTemplate);
		}else{
			success = deleteAndCreate(totalHit,indexTemplate);
		}
		
		if(success){
			logger.debug("index success:"+houseId);
		}
		
	}
	
	
	//创建索引
	public boolean create(HouseIndexTemplate houseTemplate){
		
		try {
			IndexResponse response = esClient.prepareIndex(INDEX_NAME, INDEX_TYPE).setSource(objectMapper.writeValueAsBytes(houseTemplate),XContentType.JSON)
			.get();
			if(response.status() == RestStatus.CREATED){
				return true;
			}
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	
	public boolean update(String esId,HouseIndexTemplate houseTemplate){
		
		try {
			UpdateResponse response = esClient.prepareUpdate(INDEX_NAME, INDEX_TYPE,esId).setDoc(objectMapper.writeValueAsBytes(houseTemplate),XContentType.JSON)
			.get();
			if(response.status() == RestStatus.OK){
				return true;
			}
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	
	public boolean deleteAndCreate(Long totalHit,HouseIndexTemplate houseTemplate){
		DeleteByQueryRequestBuilder builder =   DeleteByQueryAction.INSTANCE.newRequestBuilder(esClient).filter(QueryBuilders.termQuery(HouseIndexKey.HOUSE_ID, houseTemplate.getHouseId()))
		.source(INDEX_NAME);
		
		BulkByScrollResponse  scrollResponse = builder.get();
		long deleted =  scrollResponse.getDeleted();
		if(deleted != totalHit){
			logger.debug("需要删除{}，但是{}被删除",totalHit,deleted);
			return false;
		}else{
			return create(houseTemplate);
			
		}
		

	}
	
	/* (non-Javadoc)
	 * @see com.javacj.service.search.ISearchService#remove(java.lang.Long)
	 */
	@Override
	public void remove(Long houseId) {
		// TODO Auto-generated method stub
		DeleteByQueryRequestBuilder builder =   DeleteByQueryAction.INSTANCE.newRequestBuilder(esClient).filter(QueryBuilders.termQuery(HouseIndexKey.HOUSE_ID, houseId))
				.source(INDEX_NAME);
				BulkByScrollResponse  scrollResponse = builder.get();
				long deleted =  scrollResponse.getDeleted();
				logger.debug("remove success:"+deleted);
				
	}
	
}
