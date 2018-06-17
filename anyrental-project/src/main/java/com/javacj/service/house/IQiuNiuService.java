/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.service.house 
 * @author: caojun 
 * @date: 2018年5月14日 下午10:09:24 
 */
package com.javacj.service.house;

import java.io.File;
import java.io.InputStream;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 七牛云服务
 * @Package: com.javacj.service.house 
 * @author: caojun 
 * @date: 2018年5月14日 下午10:09:24 
 */
public interface IQiuNiuService {
	Response uploadFile(File file) throws QiniuException;
	Response uploadFile(InputStream is) throws QiniuException;
	
	//删除图片
	Response delete(String key) throws QiniuException;
}
