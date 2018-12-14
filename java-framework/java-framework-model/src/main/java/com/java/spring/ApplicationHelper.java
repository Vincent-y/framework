package com.java.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * <p>获取spring context
 * <p>@author dragon
 * <p>@date 2015年12月12日
 * <p>@version 1.0
 */
public class ApplicationHelper implements ApplicationContextAware{

	private ApplicationContext applicationContext;
	private static ApplicationContext context;
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
		this.applicationContext = applicationContext;
		setContext(applicationContext);
	}
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public static void setContext(ApplicationContext applicationContext){
		ApplicationHelper.context = applicationContext;
	}
	
	public static ApplicationContext getContext(){
		return ApplicationHelper.context;
	}

	public static <T> T getBean(Class<T> cls){
		return context.getBean(cls);
	}
	
}
