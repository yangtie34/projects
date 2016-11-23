package cn.gilight.product.equipment;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.gilight.framework.SpringTest;
import cn.gilight.product.equipment.service.JobEquipmentService;


public class EquipmentTest extends SpringTest{
	
	@Resource
	private JobEquipmentService jobEquipmentService;
	
	@Before
	public void testBefore(){
		System.out.println("==先执行我==");
	}
	
	@Test
	public void init(){
		jobEquipmentService.initEquipmentYear();
	}
	
	
	@After
	public void testAter(){
		System.out.println("==我最后执行==");
	}

}
