package cn.gilight.product.bookRke;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.gilight.framework.SpringTest;
import cn.gilight.product.bookRke.service.JobBookRkeService;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;


public class BookRkeTest extends SpringTest{
	
	@Resource
	private JobBookRkeService jobBookRkeService;
	
	@Before
	public void testBefore(){
		System.out.println("==先执行我==");
	}
	
//	@Test
	public void testOne(){
//		String startDate="2013-09";
//		String endDate="2013-10";
		Map<String,String> deptTeach=new HashMap<String, String>();
		deptTeach.put("istrue", "1");
		deptTeach.put("level", "0");
//		Map<String,Object> map=new HashMap<String, Object>();
//		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		
	}
	
	@Test
	public void testInit(){
		JobResultBean jrb=null;
		jrb=jobBookRkeService.initBookRkeStuMonth();
		System.out.println(jrb.getMsg());
		jrb=jobBookRkeService.initStuRkeBookMonth();
		System.out.println(jrb.getMsg());
	}	
	
	@After
	public void testAter(){
		System.out.println("==我最后执行==");
	}

}
