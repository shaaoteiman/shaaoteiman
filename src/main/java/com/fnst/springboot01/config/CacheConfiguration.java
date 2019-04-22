package com.fnst.springboot01.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/** 
 * ��������. 
 * @version v.0.1 
 */ 
@Configuration
@EnableCaching
public class CacheConfiguration {
	
	 /** 
     *  ehcache ��Ҫ�Ĺ����� 
     * @param bean 
     * @return 
     */
	@Bean
	public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean){
		return new EhCacheCacheManager(bean.getObject());
	}
	 /**
     * ��shared��������, 
     * Spring�ֱ�ͨ��CacheManager.create() 
     * ��new CacheManager()��ʽ������һ��ehcache����. 
     * 
     * Ҳ˵��˵ͨ�����������cache�Ļ����������Spring����,���Ǹ����(��hibernate��Ehcache����) 
     * 
     */ 
	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
		System.out.println("CacheConfiguration.ehCacheManagerFactoryBean()");  
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean ();  
        cacheManagerFactoryBean.setConfigLocation (new ClassPathResource("conf/ehcache.xml"));  
        cacheManagerFactoryBean.setShared(true);  
        return cacheManagerFactoryBean;  
	}
}
