package com.jhkj.mosdc.sc.util;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jhkj.mosdc.sc.service.TestOneTableQuery;


public class TestJUnit {
	
	// spring容器引用
			public ApplicationContext ac = null;
			// spring配置文件
			public static String CONFIG_FILES = "applicationContext.xml";

			// 启动spring容器
			@Before
			public void setUp() throws Exception {
				ac = new ClassPathXmlApplicationContext(CONFIG_FILES);
			}
			
			@Test
			public void test() {
				String[] s=new String[]{"XB_ID"};
				TestOneTableQuery t=(TestOneTableQuery)ac.getBean("testOneTableQuery");
				t.queryOneTable("TB_XJDA_XJXX",s , null);
			}


}
