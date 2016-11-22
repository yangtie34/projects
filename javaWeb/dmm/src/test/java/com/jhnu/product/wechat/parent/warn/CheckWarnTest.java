package com.jhnu.product.wechat.parent.warn;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jhnu.product.wechat.parent.warn.service.CheckWarnService;
import com.jhnu.spring.SpringTest;

public class CheckWarnTest extends SpringTest{
	
	@Resource
	private CheckWarnService checkWarnService;
	
	@After
	public void testAter(){
		System.out.println("==我最后执行==");
	}
	
	@Before
	public void testBefore(){
		System.out.println("==先执行我==");
	}
	
	@Test
	public void testaa(){
		checkWarnService.saveCheckWarn("2015-09-06 10:15:44");
	}
}
