/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名:  web.config.CachingConfig.java 													       
 * 功能: cpframework框架													   
 * 版本:	@version 1.0	                                                                   
 * 编制日期: 2014年9月3日 上午9:54:53 						    						                                        
 * 修改历史: (主要历史变动原因及说明)		
 * YYYY-MM-DD |    Author      |	 Change Description		      
 * 2014年9月3日    |    Administrator     |     Created 
 */
package org.pyf.developer.config;


import lombok.extern.slf4j.Slf4j;
import org.pyf.developer.web.service.cache.CommonCacheService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

/** 
 *Description: <启用缓存>. <br>
 *<p>
	<使用说明>
 </p>
 *Makedate:2014年9月3日 上午9:54:53 
 * @author Administrator  
 * @version V1.0                             
 */
@ConditionalOnProperty(prefix="redis.cache.log",name = "enabled",havingValue = "false")
@Configuration
@EnableCaching//<!-- 启用缓存注解 --> <cache:annotation-driven cache-manager="cacheManager" />
@Slf4j
public class CachingConfig {



	@Bean
	public CacheManager cacheManager() {
		log.info("SimpleCacheManager");
		SimpleCacheManager simpleCacheManager = new SimpleCacheManager();

		ConcurrentMapCache mapCache_default = new ConcurrentMapCache("default");

		ConcurrentMapCache mapCache_commonCache = new ConcurrentMapCache(
				"commonCache");

		Set<Cache> caches = new HashSet<Cache>();
		caches.add(mapCache_default);
		caches.add(mapCache_commonCache);

		simpleCacheManager.setCaches(caches);

		return simpleCacheManager;
	}

	@Bean
	public CommonCacheService commonCacheService(){
	    return new CommonCacheService();
    }




	//@Bean
	//public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
	//	EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
	//	ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource(
	//			"config/ehcache.xml"));
	//	return ehCacheManagerFactoryBean;
	//}
	//
	//@Bean(name = "cacheManager")
	//public CacheManager cacheManager() {
	//	logger.info("EhCacheCacheManager");
	//	EhCacheCacheManager cacheManager = new EhCacheCacheManager();
	//	cacheManager.setCacheManager(ehCacheManagerFactoryBean().getObject());
	//	return cacheManager;
	//}





}


