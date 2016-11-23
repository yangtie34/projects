package cn.gilight.product.net;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.gilight.framework.SpringTest;
import cn.gilight.framework.uitl.CsvUtils;
import cn.gilight.product.net.dao.NetTeaWarnDao;
import cn.gilight.product.net.service.JobNetService;
import cn.gilight.product.net.service.NetTeaWarnService;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;


public class NetTest extends SpringTest{
	
	@Resource
	private JobNetService jobNetService;
	
	@Resource
	private NetTeaWarnDao netTeaWarnDao;
	
	
	@Resource
	private NetTeaWarnService netTeaWarnService;
	
	@Before
	public void testBefore(){
		System.out.println("==先执行我==");
	}
	
	@Test
	public void init(){
		JobResultBean jrb=new JobResultBean();
		jrb=jobNetService.initNetStuMonth();
		System.out.println(jrb.getIsTrue()+"------"+jrb.getMsg());
		jrb=jobNetService.initNetTypeMonth();
		System.out.println(jrb.getIsTrue()+"------"+jrb.getMsg());
		jrb=jobNetService.initStuNumMonth();
		System.out.println(jrb.getIsTrue()+"------"+jrb.getMsg());
//		jrb=jobNetService.updateNetTrend();
//		System.out.println(jrb.getIsTrue()+"------"+jrb.getMsg());
//		jrb=jobNetService.initNetTeaWarnMonth();
//		System.out.println(jrb.getIsTrue()+"------"+jrb.getMsg());
	}
	
//	@Test
	public void testTea(){
		long startTime=0,endTime=0;
		startTime=System.currentTimeMillis();
		List<Map<String,Object>> list=netTeaWarnDao.getTeaWarn("2015-12", "2016-12", null);
		endTime=System.currentTimeMillis();System.out.println((endTime-startTime)/1000+"秒");
		startTime=System.currentTimeMillis();
		CsvUtils.createCsvFile("./1.csv",list);
		endTime=System.currentTimeMillis();System.out.println((endTime-startTime)/1000+"秒");
	}
	
	//	@Test
	public void getTeaWarn(){
		netTeaWarnService.getTeaWarn(0,0,0,"2015-12", "2016-12", null);
	}
	
	@After
	public void testAter(){
		System.out.println("==我最后执行==");
	}

}
