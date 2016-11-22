package com.jhnu.product.four.book;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jhnu.product.four.book.service.FourBookService;
import com.jhnu.spring.SpringTest;

public class BookTest extends SpringTest{
	
	@Resource
	private FourBookService fourBookService;
	
	
	@Before
	public void testBefore(){
		System.out.println("==先执行我==");
	}
	
	@Test
	public void testaa(){
	//	fourBookService.saveAllBorrowLogJob();
	//	ResultBean rb= fourBookService.getAllBorrowLog("201014040019");
	//	System.out.println(rb.getName());
		
		fourBookService.saveBookBorrowStuJob();

		
		fourBookService.saveFirstRKELogJob();
	}
	
	@After
	public void testAter(){
		System.out.println("==我最后执行==");
	}

}
