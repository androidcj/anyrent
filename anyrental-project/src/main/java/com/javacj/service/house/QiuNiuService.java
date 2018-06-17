/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.service.house 
 * @author: caojun 
 * @date: 2018年5月14日 下午10:15:17 
 */
package com.javacj.service.house;

import java.io.File;
import java.io.InputStream;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.service.house 
 * @author: caojun 
 * @date: 2018年5月14日 下午10:15:17 
 */
@Service("qiniuService")
public class QiuNiuService implements IQiuNiuService,InitializingBean{
	
	@Autowired
	private UploadManager uploadManager;
	
	@Autowired
	private BucketManager bucketManager;
	
	
	@Autowired
	private Auth auth;
	
	@Value("${qiniu.Bucket}")
	private String bucket;
	
	private StringMap putPolicy;
	
	/* (non-Javadoc)
	 * @see com.javacj.service.house.IQiuNiuService#uploadFile(java.io.File)
	 */
	@Override
	public Response uploadFile(File file) throws QiniuException {
		// TODO Auto-generated method stub
		Response response = this.uploadManager.put(file, null, getUploadToken());
		int retry = 0;		//重传3次
		while(response.needRetry() && retry<3){
			response = this.uploadManager.put(file, null, getUploadToken());
			retry++;
		}
		
		return response;
	}

	/* (non-Javadoc)
	 * @see com.javacj.service.house.IQiuNiuService#uploadFile(java.io.InputStream)
	 */
	@Override
	public Response uploadFile(InputStream is) throws QiniuException {
		// TODO Auto-generated method stub
		Response response = this.uploadManager.put(is, null, getUploadToken(),null,null);
		int retry = 0;
		while(response.needRetry() && retry<3){
			response = this.uploadManager.put(is, null, getUploadToken(),null,null);
			retry++;
		}
		return response;
	}

	/* (non-Javadoc)
	 * @see com.javacj.service.house.IQiuNiuService#delete(java.lang.String)
	 */
	@Override
	public Response delete(String key) throws QiniuException {
		// TODO Auto-generated method stub
		Response response = bucketManager.delete(this.bucket, key);
		int retry = 0;
		while(response.needRetry() && retry<3){
			response = bucketManager.delete(this.bucket, key);
			retry++;
		}
		
		return response;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		putPolicy = new StringMap();
		putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width),\"height\":$(imageInfo.height)}");
	}
	
	/**
	 * 获取上传凭证
	 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
	 * 
	 * @Package: com.javacj.service.house 
	 * @author: caojun  
	 * @date: 2018年5月14日 下午10:33:47
	 */
	private String getUploadToken(){
		return this.auth.uploadToken(bucket, null,3600,putPolicy);
	}
	
	
}
