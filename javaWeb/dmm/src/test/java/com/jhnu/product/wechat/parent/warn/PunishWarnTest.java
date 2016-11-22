package com.jhnu.product.wechat.parent.warn;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jhnu.product.wechat.parent.warn.service.PunishWarnService;
import com.jhnu.spring.SpringTest;

public class PunishWarnTest extends SpringTest{
	
	@Resource
	private PunishWarnService punishWarnService;
	
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
		punishWarnService.savePunishWarn("2015-09-06 10:15:44");
	}
}
