package com.jhnu.product.wechat.parent.score.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.product.wechat.parent.band.service.BandService;
import com.jhnu.product.wechat.parent.score.entity.WechatScoreData;
import com.jhnu.product.wechat.parent.score.service.WechatScoreService;
import com.jhnu.product.wechat.util.WechatUtil;
import com.jhnu.system.common.chart.Chart;
import com.jhnu.util.common.DateUtils;


@Controller
@RequestMapping("/wechat/parent")
public class WechatScoreController {
	@Autowired
	private WechatScoreService wechatScoreService;

	@Autowired
    private BandService bandService;

	@RequestMapping(method=RequestMethod.GET,value="/{is_wechat}/score")
	public ModelAndView getScorePage(@PathVariable("is_wechat") String is_wechat){
		ModelAndView mv=new ModelAndView("/wechat/parent/wechat");
		mv.addObject("url", "/wechat/parent/"+is_wechat+"/score");
		mv.addObject("is_wechat", WechatUtil.isWechat(is_wechat));
		return mv;
	}
	
	@RequestMapping(value="/{is_wechat}/score/{code}",method=RequestMethod.GET)
	public ModelAndView getScoreAnalyzeData(@PathVariable("is_wechat") String is_wechat,@PathVariable("code") String code){
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
			mv=new ModelAndView("redirect:/wechat/parent/score");
		}else{
			int way = 0;
			if(isWechat){
				way = 1;
			}
			bandService.addVisitLogging("08", way, DateUtils.getNowDate2(), stuId);
			mv = new ModelAndView("/wechat/parent/score/score");
			WechatScoreData wsd =  wechatScoreService.getWechatScoreData(stuId);
			Chart chart = wechatScoreService.getScoreLine(stuId);
			mv.addObject("wechatScoreData",  wsd);
			mv.addObject("chart",JSONObject.toJSON(chart));
			mv.addObject("stuId", stuId);
			mv.addObject("is_wechat", isWechat);
		}
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="/score/line/{stuId}",method=RequestMethod.POST)
	public ResultBean getScoreLineData(@PathVariable String stuId){
		ResultBean rb = new ResultBean();
		if(stuId.equals("")||stuId.equals("error")){
			rb.setTrue(false);
		}else{
			Chart chart = wechatScoreService.getScoreLine(stuId);
			rb.setObject(chart);
		}
		return rb;
	}
}
