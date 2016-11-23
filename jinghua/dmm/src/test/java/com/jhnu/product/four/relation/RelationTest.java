package com.jhnu.product.four.relation;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jhnu.product.four.relation.service.FourRelationService;
import com.jhnu.spring.SpringTest;

public class RelationTest  extends SpringTest{
	
	@Resource
	private FourRelationService fourRelationService;
	
	
	@Before
	public void testBefore(){
		System.out.println("==先执行我==");
	}
	
	@Test
	public void testRelation(){
		fourRelationService.saveRoommateJob();
		System.out.println("====================");
		fourRelationService.saveTutorJob();
	}
	
	@After
	public void testAter(){
		System.out.println("==我最后执行==");
	}

}
