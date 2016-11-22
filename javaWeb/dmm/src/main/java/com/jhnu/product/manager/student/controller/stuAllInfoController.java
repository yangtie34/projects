package com.jhnu.product.manager.student.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.product.manager.student.service.IStuAllInfoService;
import com.jhnu.util.common.MapUtils;

@Controller
@RequestMapping("/manager/stuAll")
public class stuAllInfoController {

	@Autowired
	private IStuAllInfoService stuAllInfoService;

	@RequestMapping(method = RequestMethod.GET, value = "/allinfo")
	public ModelAndView getStuDeptInfo() {
		ModelAndView mv = new ModelAndView("/manager/student/stuallinfo");
		return mv;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, value = "/allinfo/{stuId}")
	public ModelAndView getStuDept(@PathVariable("stuId") String stuId) {

		ModelAndView mv = new ModelAndView("/manager/student/stuallinfo");
		// 学籍基本信息开始
		List<Map<String, Object>> stuInfo = stuAllInfoService.getStuInfo(stuId);
		Map<String, Object> stuMap = new HashMap<String, Object>();
		if (stuInfo != null) {
			stuMap = stuInfo.get(0);
		}
		mv.addObject("stuMap", stuMap);
		// 学籍基本信息结束

		// 荣誉资助信息开始
		List<Map<String, Object>> awards = stuAllInfoService.getAward(stuId);
		List<Map<String, Object>> subsidys = stuAllInfoService.getSubsidy(stuId);
		List<Map<String, Object>> awardAndSubsidy = new ArrayList<Map<String, Object>>();
		int awardNumers = 0;
		int subsidyNumbers = 0;
		if (awards != null) {
			awardNumers = awards.size();
			for (Map<String, Object> map : awards) {
				awardAndSubsidy.add(map);
			}
		}
		if (subsidys != null) {
			subsidyNumbers = subsidys.size();
			for (Map<String, Object> map : subsidys) {
				awardAndSubsidy.add(map);
			}
		}
		mv.addObject("awardAndSubsidy", awardAndSubsidy);
		mv.addObject("awardNumers", awardNumers);
		mv.addObject("subsidyNumbers", subsidyNumbers);
		// 荣誉资助信息结束

		// 室友信息开始
		List<Map<String, Object>> roommate = stuAllInfoService.getRoommate(stuId);
		mv.addObject("roommate", roommate);
		// 室友信息结束
		
		// 违纪处分
		Map<String,Object> punishMap = stuAllInfoService.stusPunishmentViolation(stuId);
		List<Map<String, Object>> punishList = (List<Map<String, Object>>) punishMap.get("punishInfoList");	// 学生的违纪处分信息
		List<Map<String, Object>> violateNumsList = (List<Map<String, Object>>) punishMap.get("violateNumsList"); // 学生的违纪处分中违纪类型的次数
		String stuName ="";
		if(violateNumsList != null && violateNumsList.size()>0){
		    stuName = MapUtils.getString(violateNumsList.get(0), "STUNAME");
		}
		String stuDate ="";
		String violateName ="";
		String punishName ="";
		if(punishList!= null && punishList.size()>0){
			stuDate = MapUtils.getString(punishList.get(0),"STUDATE");
			violateName = MapUtils.getString(punishList.get(0),"VIOLATENAME");
			punishName = MapUtils.getString(punishList.get(0),"PUNISHNAME");
		}
		mv.addObject("punishList",punishList);
		mv.addObject("violateNumsList",violateNumsList);
		mv.addObject("stuName",stuName);
		mv.addObject("stuDate",stuDate);
		mv.addObject("violateName",violateName);
		mv.addObject("punishName",punishName);
		return mv;
	}
}
