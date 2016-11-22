package com.jhnu.product.four.award;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



import com.jhnu.product.four.award.service.FourAwardService;
import com.jhnu.spring.SpringTest;

public class AwardTest extends SpringTest{
  
	
	@Resource
	private FourAwardService fourAwardService;
	
	
	@Before
	public void testBefore(){
		System.out.println("==先执行我==");
	}
	
	@Test
	public void testaa(){
	//	fourBookService.saveAllBorrowLogJob();
	//	fourAwardService.SaveAllAwardTimesToLog();
		
		fourAwardService.SaveAllSubsidy();
	}
	
	@After
	public void testAter(){
		System.out.println("==我最后执行==");
	}

	
	
}
