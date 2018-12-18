package com.unicomoa.unicomoa.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)  {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}
	
	public static <T> T getBean(Class<T> cls){
		return applicationContext.getBean(cls);
	}

	public static <T> T getBean(String name){
		return (T) applicationContext.getBean(name);
	}
	
	public static ApplicationContext getContext(){
		return applicationContext;
	}
}
