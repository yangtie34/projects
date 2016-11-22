package com.jhnu.product.four.card;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.common.stu.service.StuService;
import com.jhnu.product.four.card.service.FourCardService;
import com.jhnu.spring.SpringTest;
import com.jhnu.system.permiss.service.impl.PasswordHelperImpl;
import com.jhnu.system.permiss.service.impl.UserServiceImpl;

public class CardTest extends SpringTest{
	@Resource
	private FourCardService fourCardService;
	@Resource
	private StuService stuService;
	@Resource
	private UserServiceImpl userService;
	@Autowired
	private PasswordHelperImpl passwordHelper;
	
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
//		fourCardService.saveFirstJob();
//		fourCardService.saveMealsCount();
//		fourCardService.saveMealsAvg();
	//	fourCardService.saveFirstJob();
		
	//	fourCardService.savePayCountJob();
//		System.out.println("===");
//		long bl=System.currentTimeMillis();
//		fourCardService.saveLikeEatJob();
//		System.out.println("总用时："+(System.currentTimeMillis()-bl));
		
//		fourCardService.saveRecCountJob();
//		System.out.println("5");
//		fourCardService.saveAllRecCountJob();
		
	}
}
