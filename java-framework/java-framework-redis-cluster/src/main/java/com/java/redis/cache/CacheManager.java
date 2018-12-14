package com.java.redis.cache;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <p>缓存管理器
 * <p>@author @DRAGON-Yeah
 * <p>@date 2016年6月25日
 * <p>@version 1.0
 */
public class CacheManager implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	private static final CacheManager instance = new CacheManager();
	
	private SessionCache sessionCache;
	
	private String beanName;
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		CacheManager.applicationContext = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}

    public static SessionCache getSessionCache() {
    	if(instance.sessionCache != null){
    		return instance.sessionCache;
    	}else{
    		if(instance.beanName == null){
    			instance.sessionCache = (SessionCache) applicationContext.getBean(instance.beanName);
    		}
    	}
        return instance.sessionCache;
    }
    
	public void setSessionCache(SessionCache sessionCache) {
		instance.sessionCache = sessionCache;
	}

	public void setBeanName(String beanName) {
		instance.beanName = beanName;
	}
	
}
