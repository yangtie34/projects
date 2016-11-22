package com.jhnu.product.four.punish;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jhnu.product.four.punish.service.FourPunishService;
import com.jhnu.spring.SpringTest;

public class PunishTest extends SpringTest{

	@Resource
	private FourPunishService fourPunishService;
	
	
	@After
	public void testAfter() {
		// TODO Auto-generated method stub
        System.out.println("最后执行我");
	}
	
	@Before
	public void testBefore(){
		System.out.println("先执行我");
	}
	
	@Test
	public void testing(){
		fourPunishService.savePunishLog();
	}
}
