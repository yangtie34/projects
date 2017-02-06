package cn.gilight.dmm.teaching.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.teaching.service.ScoreByStuLbService;

@Controller
@RequestMapping("scoreByStuLb")
public class ScoreByStuLbCtol {
	
	@Resource
	private ScoreByStuLbService scoreByStuLbService;
	
	@RequestMapping()
	public String init(){
		return "scoreByStuLb";
	}
	
	@RequestMapping("/getXnXqList")
	@ResponseBody
	public List<Map<String, Object>> getXnXqList(){
		return scoreByStuLbService.getXnXqList();
	}
	
	@RequestMapping("/getTargetList")
	@ResponseBody
	public List<Map<String, Object>> getTargetList(){
		return scoreByStuLbService.getTargetList();
	}
	
	@RequestMapping("/getCourseTypeList")
	@ResponseBody
	public List<Map<String, Object>> getCourseTypeList(String schoolYear,String termCode,String param){
		return scoreByStuLbService.getCourseTypeList(schoolYear, termCode, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("/getCourseCategoryList")
	@ResponseBody
	public List<Map<String, Object>> getCourseCategoryList(String schoolYear,String termCode,String param){
		return scoreByStuLbService.getCourseCategoryList(schoolYear, termCode, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("/getCourseAttrList")
	@ResponseBody
	public List<Map<String, Object>> getCourseAttrList(String schoolYear,String termCode,String param){
		return scoreByStuLbService.getCourseAttrList(schoolYear, termCode, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("/getCourseNatureList")
	@ResponseBody
	public List<Map<String, Object>> getCourseNatureList(String schoolYear,String termCode,String param){
		return scoreByStuLbService.getCourseNatureList(schoolYear, termCode, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("/getCourseList")
	@ResponseBody
	public List<Map<String, Object>> getCourseList(String schoolYear, String termCode,
			String type, String category, String attr, String nature,String param){
		return scoreByStuLbService.getCourseList(schoolYear, termCode,type,category,attr,nature,AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("/getScoreByOriginList")
	@ResponseBody
	public Map<String, Object> getScoreByOriginList(String schoolYear, String termCode,
			String type, String category, String attr, String nature,String course,String tag,String param){
		return scoreByStuLbService.getScoreByOriginList(schoolYear, termCode, type, category, attr, nature, course, tag, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("/getScoreByNationList")
	@ResponseBody
	public Map<String, Object> getScoreByNationList(String schoolYear, String termCode,
			String type, String category, String attr, String nature,String course,String tag,String param){
		return scoreByStuLbService.getScoreByNationList(schoolYear, termCode, type, category, attr, nature, course, tag, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("/getScoreByZzmmList")
	@ResponseBody
	public Map<String, Object> getScoreByZzmmList(String schoolYear, String termCode,
			String type, String category, String attr, String nature,String course,String tag,String param){
		return scoreByStuLbService.getScoreByZzmmList(schoolYear, termCode, type, category, attr, nature, course, tag, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("/getScoreFbByOrigin")
	@ResponseBody
	public List<Map<String, Object>> getScoreFbByOrigin(String schoolYear,
			String termCode, String type, String category, String attr,
			String nature, String course, String originId,String param){
		return scoreByStuLbService.getScoreFbByOrigin(schoolYear, termCode, type, category, attr, nature, course,originId,AdvancedUtil.converAdvancedList(param));
	}
	@RequestMapping("/getScoreFbByZzmm")
	@ResponseBody
	public List<Map<String, Object>> getScoreFbByZzmm(String schoolYear,
			String termCode, String type, String category, String attr,
			String nature, String course, String zzmmId,String param){
		return scoreByStuLbService.getScoreFbByZzmm(schoolYear, termCode, type, category, attr, nature, course,zzmmId,AdvancedUtil.converAdvancedList(param));
	}
	@RequestMapping("/getScoreFbByNation")
	@ResponseBody
	public List<Map<String, Object>> getScoreFbByNation(String schoolYear,
			String termCode, String type, String category, String attr,
			String nature, String course, String nationId,String param){
		return scoreByStuLbService.getScoreFbByNation(schoolYear, termCode, type, category, attr, nature, course,nationId,AdvancedUtil.converAdvancedList(param));
	}
}
