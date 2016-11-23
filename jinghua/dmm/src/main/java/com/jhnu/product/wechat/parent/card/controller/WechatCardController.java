package com.jhnu.product.wechat.parent.card.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.product.wechat.parent.band.service.BandService;
import com.jhnu.product.wechat.parent.card.service.WechatCardService;
import com.jhnu.product.wechat.util.WechatUtil;
import com.jhnu.util.common.DateUtils;

@Controller
@RequestMapping("/wechat/parent")
public class WechatCardController {
	@Autowired
	private WechatCardService wechatCardService;
	@Autowired
	private BandService bandService;
	
	@RequestMapping(method=RequestMethod.GET,value="/{is_wechat}/card/{timeType}")
	public ModelAndView getCardPage(@PathVariable("is_wechat") String is_wechat,@PathVariable("timeType") String timeType){
		ModelAndView mv=new ModelAndView("/wechat/parent/wechat");
		mv.addObject("url", "/wechat/parent/"+is_wechat+"/card/"+timeType);
		mv.addObject("is_wechat", WechatUtil.isWechat(is_wechat));
		return mv;
	}
	
	
	@RequestMapping(value="/{is_wechat}/card/{timeType}/{code}",method=RequestMethod.GET)
	public ModelAndView getCardAnalyzeData(@PathVariable("is_wechat") String is_wechat,@PathVariable("code") String code,@PathVariable("timeType") String timeType){
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
		}else if(stuId.equals("error")&&isWechat){
			mv=new ModelAndView("redirect:/wechat/parent/card/default");
		}else{
			int way = 0;
			if(isWechat){
				way = 1;
			}
			bandService.addVisitLogging("06", way, DateUtils.getNowDate2(), stuId);
			mv = new ModelAndView("/wechat/parent/card/card");
			mv.addObject("cardAnalyzeData", wechatCardService.getAnalyzeData(stuId, timeType));
			mv.addObject("timeType","default".equals(timeType)?"rxyl":timeType);
			
			mv.addObject("stuId", stuId);
			mv.addObject("is_wechat", isWechat);
		}

		return mv;
	}
}
