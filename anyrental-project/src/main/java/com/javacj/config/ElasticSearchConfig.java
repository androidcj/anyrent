/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.config 
 * @author: caojun 
 * @date: 2018年6月17日 下午9:38:27 
 */
package com.javacj.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * ES配置类
 * @Package: com.javacj.config 
 * @author: caojun 
 * @date: 2018年6月17日 下午9:38:27 
 */
@Configuration
public class ElasticSearchConfig {
	@Bean
	public TransportClient esClient() throws UnknownHostException{
		Settings settings = Settings.builder().put("cluster.name","elasticsearch")
				.put("client.transport.sniff",true).build();
		
		InetSocketTransportAddress master = new InetSocketTransportAddress(InetAddress.getByName("192.168.1.3"),9300);
		TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(master);   //此处如果有很多节点  可以在这里初始化
		return client;
	}
}
