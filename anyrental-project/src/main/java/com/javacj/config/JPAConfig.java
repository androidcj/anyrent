package com.javacj.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;



/**   
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.androidcj.config 
 * @author: caojun 
 * @date: 2018年5月2日 下午9:02:25 
 */
@Configuration
@EnableJpaRepositories(basePackages="com.javacj.repository")
@EnableTransactionManagement
public class JPAConfig {
	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource(){
		System.out.println("jpa=====================333");
		//建立数据源
		return DataSourceBuilder.create().build();
	}  
	
	//实体类的管理工厂
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		System.out.println("jpa======================111");
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		//是否生成sql   设置成false
		jpaVendorAdapter.setGenerateDdl(false);
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		//设置数据源
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter); 
		//设置实体类包名
		entityManagerFactory.setPackagesToScan("com.javacj.entity");
		return entityManagerFactory;
	}
	
	@Bean 
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
		System.out.println("jpa=====================222");
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	} 
}
