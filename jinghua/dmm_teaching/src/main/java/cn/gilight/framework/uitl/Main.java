package cn.gilight.framework.uitl;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * 
 * @author xuebl
 * @date 2017年1月11日 下午1:13:42
 */
public class Main {
	
	
	public static ClassPathXmlApplicationContext getContext() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-mvc.xml");
		return context;
	}
	
}
