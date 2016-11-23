package com.jhnu.product.wechat.parent.check;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jhnu.product.wechat.parent.check.service.WechatCheckService;
import com.jhnu.spring.SpringTest;

public class CheckServiceImplTest extends SpringTest{

	@Resource
	private WechatCheckService checkService;
	
	@Before
	public void testBefore(){
		System.out.println("==先执行我==");
	}
	
	@Test
	public void testCheck(){
		
		checkService.saveCheckLog();
		checkService.saveStuCutClassLog();
		
		List<Map<String,Object>> checkList = checkService.getCheckLog("13160821131029");
		List<Map<String,Object>> checkOftenList =  checkService.getStuCutClassLog("1001000000716359");
		System.out.println(checkList.toString());
		System.out.println(checkOftenList.toString());
	}
	
	@After
	public void testAter(){
		System.out.println("==我最后执行==");
	}


}
