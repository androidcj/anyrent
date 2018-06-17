/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.config 
 * @author: caojun 
 * @date: 2018年5月5日 下午9:24:53 
 */
package com.javacj.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.javacj.config 
 * @author: caojun 
 * @date: 2018年5月5日 下午9:24:53 
 */
	
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware{

	private ApplicationContext applicationContext;


	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = arg0;
		
	}
	
	//解析模板资源
    @Bean
    @ConfigurationProperties(prefix = "spring.thymeleaf")
	public SpringResourceTemplateResolver templateResolver(){
    	SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setApplicationContext(this.applicationContext);
		templateResolver.setCharacterEncoding("UTF-8");
		return templateResolver;
	}
	
	//thymeleaf 标准方言解释器
	@Bean 
	public SpringTemplateEngine templateEngine(){
		SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
		springTemplateEngine.setTemplateResolver(templateResolver());
		//支持el表达式
		springTemplateEngine.setEnableSpringELCompiler(true);
		
		//支持springsecurity方言
		SpringSecurityDialect ssd = new SpringSecurityDialect();
		springTemplateEngine.addDialect(ssd);
		return springTemplateEngine;
	}
	
	
	//视图解析器
	@Bean
	public ThymeleafViewResolver viewResolver(){
		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
		thymeleafViewResolver.setTemplateEngine(templateEngine());
		return thymeleafViewResolver;
	}
	
	
	
	//静态资源加载配置
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		
	}
	
	
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	
}
