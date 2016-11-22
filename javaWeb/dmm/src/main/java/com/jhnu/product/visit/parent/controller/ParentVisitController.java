package com.jhnu.product.visit.parent.controller;


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
import com.jhnu.product.visit.parent.service.ParentVisitService;
import com.jhnu.util.common.DateUtils;
import com.jhnu.util.common.MapUtils;

@Controller
@RequestMapping("/visit")
public class ParentVisitController {

	@Autowired
	private ParentVisitService parentVisitService;
	
	@RequestMapping(method=RequestMethod.GET,value="/parent")
	public ModelAndView getParentVisitCounts(){
		ModelAndView mv = new ModelAndView("/visit/visitParent");
		String start = DateUtils.getLastMonth();
		String end = DateUtils.getNowDate();
		mv.addObject("start", start);
		mv.addObject("end", end);
 		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="/parent/{startDate}/{endDate}",method=RequestMethod.POST)
	public ResultBean getScoreLineData(@PathVariable String startDate,@PathVariable String endDate){
		ResultBean rb = new ResultBean();
		Map<String,Object> totalMap = parentVisitService.getVisitTotalCounts(startDate, endDate);
		Map<String,Object> pcMap = parentVisitService.getVisitPcCounts(startDate, endDate);
		Map<String,Object> mobelMap = parentVisitService.getVisitMobelCounts(startDate, endDate);
		//Map<String,Object> userMap = parentVisitService.getUserCounts(startDate, endDate);
		
		//Map<String,Object> parentNums = parentVisitService.getParentNums(startDate, endDate);
		List<Map<String,Object>> list = parentVisitService.getParentVisitCounts(startDate, endDate);
		//List<Map<String,Object>> countList = parentVisitService.getOtherVisitCounts(startDate,endDate);
		int total_counts = 0;
		int url_counts = 0;
		int wechat_counts = 0;
		if(list != null && list.size()>0){
			for(Map<String,Object> map : list){
				int counts = MapUtils.getIntValue(map, "NUMS");
				int is_wechat = MapUtils.getIntValue(map, "IS_WECHAT"); 
				if(is_wechat == 0){
					url_counts = counts;
				}else{
					wechat_counts = counts;
				}
				total_counts += counts;
			}
		}
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("total_counts", MapUtils.getIntValue(totalMap, "TOTAL_COUNTS"));
		map.put("pc_counts", MapUtils.getIntValue(pcMap, "PC_COUNTS"));
		map.put("mobel_counts", MapUtils.getIntValue(mobelMap, "MOBEL_COUNTS"));
		//map.put("user_counts", MapUtils.getIntValue(userMap, "USER_COUNTS"));
		//map.put("parent_nums", MapUtils.getIntValue(parentNums, "PARENT_NUMS"));
		
		map.put("wechat_counts", wechat_counts);
		map.put("url_counts", url_counts);
		map.put("total_home_counts", total_counts);
		//map.put("countList", countList);
		
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		rb.setData(map);
		return rb;
	}

}
