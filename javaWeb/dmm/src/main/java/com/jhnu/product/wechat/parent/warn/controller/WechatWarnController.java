package com.jhnu.product.wechat.parent.warn.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.product.wechat.parent.band.service.BandService;
import com.jhnu.product.wechat.parent.warn.entity.WechatWarn;
import com.jhnu.product.wechat.parent.warn.service.WechatWarnService;
import com.jhnu.product.wechat.util.WechatUtil;
import com.jhnu.system.common.page.Page;
import com.jhnu.util.common.DateUtils;

@Controller
@RequestMapping("/wechat/parent")
public class WechatWarnController {
	
	@Autowired
	private WechatWarnService wechatWarnService;
	@Autowired
	private BandService bandService;
	
	
	@RequestMapping(method=RequestMethod.GET,value="/{is_wechat}/warn")
	public ModelAndView getDayPage(@PathVariable("is_wechat") String is_wechat){
		ModelAndView mv=new ModelAndView("/wechat/parent/wechat");
		mv.addObject("url", "/wechat/parent/"+is_wechat+"/warn");
		mv.addObject("is_wechat", WechatUtil.isWechat(is_wechat));
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method=RequestMethod.GET,value="/{is_wechat}/warn/{code}")
	public ModelAndView getWarn(@PathVariable("is_wechat") String is_wechat,@PathVariable("code") String code){
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
			mv=new ModelAndView("redirect:/wechat/parent/warn");
		}else{
			int way = 0;
			if(isWechat){
				way = 1;
			}
			bandService.addVisitLogging("05", way, DateUtils.getNowDate2(), stuId);
			
			Page page=new Page();
			page=wechatWarnService.getWechatWarns(stuId, page);
			List<WechatWarn> warns= (List<WechatWarn>)page.getResultListObject();
			wechatWarnService.readWechatWarns(warns);
			mv=new ModelAndView("/wechat/parent/warn/warn");
			mv.addObject("page", page);
			mv.addObject("stuId", stuId);
			mv.addObject("warns", warns);
			mv.addObject("is_wechat", isWechat);
		}
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST,value="/warn/{stuid}/{current}")
	public ResultBean getWarnForPage(@PathVariable("stuid") String stuid,@PathVariable("current") int current){
		ResultBean rb=new ResultBean();
		Page page=new Page(current);
		page=wechatWarnService.getWechatWarns(stuid, page);
		List<WechatWarn> warns= (List<WechatWarn>)page.getResultListObject();
		wechatWarnService.readWechatWarns(warns);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("warns", warns);
		rb.setData(map);
		rb.setObject(page);
		return rb;
	}
	
}
