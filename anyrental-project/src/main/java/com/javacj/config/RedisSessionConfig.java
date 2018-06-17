/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.config 
 * @author: caojun 
 * @date: 2018年6月7日 下午8:48:24 
 */
package com.javacj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * session共享
 * @Package: com.javacj.config 
 * @author: caojun 
 * @date: 2018年6月7日 下午8:48:24 
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=86400)
public class RedisSessionConfig {
	@Bean
	public RedisTemplate<String,String> redisTemplate(RedisConnectionFactory factory){
		return new StringRedisTemplate(factory);
	}
	
	  
	
}
