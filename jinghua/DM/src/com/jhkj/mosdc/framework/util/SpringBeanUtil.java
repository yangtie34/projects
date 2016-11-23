package com.jhkj.mosdc.framework.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class SpringBeanUtil implements ApplicationContextAware {
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		// TODO Auto-generated method stub
		SpringBeanUtil.context = context;
	}
	public static Object getBean(String beanId){
		return context.getBean(beanId);
	}

}
