package cn.gilight.product.dorm;

import javax.annotation.Resource;

import org.junit.Test;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

import cn.gilight.framework.SpringTest;
import cn.gilight.product.dorm.service.DormJobService;

public class DormTest extends SpringTest{

	@Resource
	private DormJobService dormJobService;
	
	
	@Test
	public void test(){
//		JobResultBean rjb= dormJobService.dormStuJob();
//		System.out.println(rjb.getMsg());
		
		JobResultBean rjb2=dormJobService.initDormRkeUsedStuJob();
		System.out.println(rjb2.getMsg());
//		
		JobResultBean rjb3=dormJobService.initDormTrend();
		System.out.println(rjb3.getMsg());
		
		
	}
	
}
