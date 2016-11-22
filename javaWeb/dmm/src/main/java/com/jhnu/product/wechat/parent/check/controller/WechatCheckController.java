package com.jhnu.product.wechat.parent.check.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.product.wechat.parent.band.service.BandService;
import com.jhnu.product.wechat.parent.check.service.WechatCheckService;
import com.jhnu.product.wechat.util.WechatUtil;
import com.jhnu.util.common.DateUtils;
import com.jhnu.util.common.MapUtils;
import com.jhnu.util.product.EduUtils;

@Controller
@RequestMapping("/wechat/parent")
public class WechatCheckController {
	@Autowired
	private BandService bandService;
	
	@Autowired
	private WechatCheckService checkService;
	
	@RequestMapping(method=RequestMethod.GET,value="/{is_wechat}/check")
	public ModelAndView getCheckPage(@PathVariable("is_wechat") String is_wechat){
		ModelAndView mv=new ModelAndView("/wechat/parent/wechat");
		mv.addObject("url", "/wechat/parent/"+is_wechat+"/check");
		mv.addObject("is_wechat",WechatUtil.isWechat(is_wechat));
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{is_wechat}/check/{wechat_code}")
	public ModelAndView getCheck(@PathVariable("is_wechat") String is_wechat,@PathVariable("wechat_code") String wechat_code){
		ModelAndView mv ;
		String stuId="";
		boolean isWechat=WechatUtil.isWechat(is_wechat);
		if(isWechat){
			stuId=bandService.getStuIdByWechatCode(wechat_code);
		}else{
			stuId=wechat_code;
		}
		if(stuId.equals("")&&isWechat){
			mv=new ModelAndView("redirect:/wechat/parent/band");
		}else if(stuId.equals("error")&&isWechat){
			mv=new ModelAndView("redirect:/wechat/parent/check");
		}else{
			int way = 0;
			if(isWechat){
				way = 1;
			}
			bandService.addVisitLogging("07", way, DateUtils.getNowDate2(), stuId);
			List<Map<String,Object>> list = checkService.getCheckLog(stuId);
			List<Map<String,Object>> oftenList = checkService.getStuCutClassLog(stuId);
			
			String[] strs = EduUtils.getSchoolYearTerm(new Date());
			String school_year = strs[0];
			String term_code = strs[1];
			mv = new ModelAndView("/wechat/parent/check/checks");
			for(Map<String,Object> map : list){
				if(school_year.equals(map.get("school_year")) && term_code.equals(map.get("term_code"))){
					mv.addObject("check", map);
				}
			}
			for(Map<String,Object> map : oftenList){
				if(school_year.equals(map.get("school_year")) && term_code.equals(map.get("term_code"))){
					if(MapUtils.getString(map,"often_late_class").equals("")){
						map.remove("often_late_class");
					}
					if(MapUtils.getString(map,"often_cut_class").equals("")){
						map.remove("often_cut_class");
					}
					mv.addObject("check_often", map);
				}
			}
			mv.addObject("stuId", stuId);
			mv.addObject("is_wechat", isWechat);
		}
		return mv;
	}
}
