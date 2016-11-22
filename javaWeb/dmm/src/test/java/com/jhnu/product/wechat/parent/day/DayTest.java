package com.jhnu.product.wechat.parent.day;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jhnu.product.common.course.service.CourseService;
import com.jhnu.product.wechat.parent.day.service.WechatDayService;
import com.jhnu.spring.SpringTest;
import com.jhnu.util.common.DateUtils;

public class DayTest extends SpringTest{
	@Resource
	private WechatDayService wechatDayService;
	@Resource
	private CourseService courseService;
	
	@After
	public void testAter(){
		System.out.println("==我最后执行==");
	}
	
	@Before
	public void testBefore(){
		System.out.println("==先执行我==");
	}
	
	@Test
	public void testaa(){
	//	courseService.initCourseWeekJob();
		wechatDayService.exeDayJobByCardTime(DateUtils.SSS.format(new Date()));
	//	wechatDayService.moveDayToLog("2015-09-22");
	//	wechatDayService.exeDayJobByDay("2014-09-22");
	//	wechatDayService.exeDayJobByCardTime(DateUtils.SSS.format(new Date()));
		//	wechatDayService.exeDayJobByCardTime("2015-09-23 10:00:00");
		//	wechatDayService.exeDayJobByBookRkeTime("2015-09-23 10:00:00");
	//	wechatDayService.exeDayJobByDormRKETime("2015-09-23 10:00:00");
		
	}
}
