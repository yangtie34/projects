package com.jhnu.product;

import javax.annotation.Resource;

import org.junit.Test;

import com.jhnu.spring.SpringTest;
import com.jhnu.util.product.ZcService;

public class ZcTest extends SpringTest{
	@Resource
	private ZcService zcService;
	
	@Test
	public void testaa(){
		zcService.addZc();
		
	}
	
}

