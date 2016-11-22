package cn.gilight.product.book;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.gilight.framework.SpringTest;

import com.jhnu.system.permiss.job.UserJob;
import com.jhnu.system.permiss.service.UserService;


public class UserTest extends SpringTest{
	
	@Resource
	private UserService userService;
	
	@Resource
	private UserJob userJob;
	
	@Before
	public void testBefore(){
		System.out.println("==先执行我==");
	}

	@Test
	public void test(){
		
	}
	
	@After
	public void testAter(){
		System.out.println("==我最后执行==");
	}

}
