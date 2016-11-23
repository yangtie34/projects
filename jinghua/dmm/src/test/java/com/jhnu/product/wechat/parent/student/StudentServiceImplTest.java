package com.jhnu.product.wechat.parent.student;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jhnu.product.wechat.parent.student.service.WechatStudentService;
import com.jhnu.spring.SpringTest;

public class StudentServiceImplTest extends SpringTest{

	@Resource
	private WechatStudentService studentService;
	
	@Before
	public void testBefore(){
		System.out.println("==先执行我==");
	}
	
	@Test
	public void testStudent(){
		List<Map<String,Object>> map = studentService.getStudentInfo("13140902SG1048");
		List<Map<String,Object>> map1 = studentService.getDormInfo("13140902SG1048");
		
		List<Map<String,Object>> list3 = studentService.getDeptInfo();
		System.out.println(list3.toString());
		
		List<Map<String,Object>> list = studentService.getRoommate("13140902SG1048");
		List<Map<String,Object>> list1 = studentService.getAward("1001000011859634");
		List<Map<String,Object>> list2 = studentService.getSubsidy("1000000000000776");
		
		System.out.println(map.toString());
		System.out.println(map1.toString());
		System.out.println(list.toString());
		
		System.out.println(list1.toString());
		System.out.println(list2.toString());
		
	}
	
	@After
	public void testAter(){
		System.out.println("==我最后执行==");
	}


}
