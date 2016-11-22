package com.jhnu.product.wechat.parent.day.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.product.common.stu.entity.Student;
import com.jhnu.product.common.stu.service.StuService;
import com.jhnu.product.wechat.parent.band.service.BandService;
import com.jhnu.product.wechat.parent.day.entity.WechatDay;
import com.jhnu.product.wechat.parent.day.service.WechatDayService;
import com.jhnu.product.wechat.util.WechatUtil;
import com.jhnu.util.common.DateUtils;

@Controller
@RequestMapping("/wechat/parent")
public class WechatDayController {
	
	@Autowired
	private WechatDayService wechatDayService;
	@Autowired
	private BandService bandService;
	@Autowired StuService stuService;
	
	@RequestMapping(method=RequestMethod.GET,value="/{is_wechat}/day/{dayt}")
	public ModelAndView getDayPage(@PathVariable("is_wechat") String is_wechat,@PathVariable("dayt") String dayt){
		ModelAndView mv=new ModelAndView("/wechat/parent/wechat");
		mv.addObject("url", "/wechat/parent/"+is_wechat+"/day/"+dayt);
		mv.addObject("is_wechat", WechatUtil.isWechat(is_wechat));
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{is_wechat}/day/{dayt}/{code}")
	public ModelAndView getDay(@PathVariable("is_wechat") String is_wechat,@PathVariable("dayt") String dayt,@PathVariable("code") String code){
		ModelAndView mv;
		String stuId="";
		boolean isWechat=WechatUtil.isWechat(is_wechat);
		if(isWechat){
			stuId=bandService.getStuIdByWechatCode(code);
		}else{
			stuId=code;
		}
		if(stuId.equals("")&&isWechat){
			mv=new ModelAndView("redirect:/wechat/parent/band");
		}else if(stuId.equals("erroe")&&isWechat){
			mv=new ModelAndView("redirect:/wechat/parent/day/today");
		}else{
			int way = 0;
			if(isWechat){
				way = 1;
			}
			bandService.addVisitLogging("04", way, DateUtils.getNowDate2(), stuId);
			
			String date=dayt;
			if(dayt.equals("today")){
				date=DateUtils.SDF.format(new Date());
			}else if(dayt.equals("yesterday")){
				date=DateUtils.getYesterday();
			}else if(dayt.equals("tomorrow")){
				date=DateUtils.getNextDay(DateUtils.SDF.format(new Date()));
			}
			Student stu=stuService.getStudentInfo(stuId);
			if(stu==null){
				//TODO 此处设置为无权限不友好，提供提示具体已知信息的页面（没有该用户ID）
				mv=new ModelAndView("/unauthorized");
				return mv;
			}
			WechatDay day=new WechatDay(stuId,stu.getClass_id(),date);
			
			List<WechatDay> days=wechatDayService.getWechatDaysByThis(day);
			mv=new ModelAndView("/wechat/parent/day/day");
			mv.addObject("days", days);
			mv.addObject("dayt", dayt);
			mv.addObject("stuId", stuId);
			mv.addObject("is_wechat", isWechat);
		}
		return mv;
	}
	
}
