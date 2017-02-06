package cn.gilight.dmm.teaching.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.teaching.service.CourseService;

@Controller
@RequestMapping("course")
public class CourseCtol {
	@Resource
	private CourseService courseService;
	
	@RequestMapping()
	public String init(){
		return "course";
	}
	
	@RequestMapping("getBzdm")
	@ResponseBody
	public Map<String,Object> getBzdm(){
		return courseService.getBzdm();
	}
	
	@RequestMapping("getCourseDistribution")
	@ResponseBody
	public Map<String,Object> getCourseDistribution(String param,String edu,String schoolYear,String termCode){
		return courseService.getCourseDistribution(AdvancedUtil.converAdvancedList(param), edu, schoolYear, termCode);
	}
	@RequestMapping("getDeptDistribution")
	@ResponseBody
	public Map<String,Object> getDeptDistribution(String param,String edu,String schoolYear,String termCode,String codeType,String code){
		return courseService.getDeptDistribution(AdvancedUtil.converAdvancedList(param), edu, schoolYear, termCode, codeType, code);
	}
	
	@RequestMapping("getSubjectDistribution")
	@ResponseBody
	public Map<String,Object> getSubjectDistribution(String param,String edu,String schoolYear,String termCode,String codeType,String code){
		return courseService.getSubjectDistribution(AdvancedUtil.converAdvancedList(param), edu, schoolYear, termCode, codeType, code);
	}
	
	@RequestMapping("getDeptHistory")
	@ResponseBody
	public Map<String,Object> getDeptHistory(String param,String edu,String codeType,String code){
		return courseService.getDeptHistory(AdvancedUtil.converAdvancedList(param), edu, codeType, code);
	}
	
	@RequestMapping("getSubjectHistory")
	@ResponseBody
	public Map<String,Object> getSubjectHistory(String param,String edu,String codeType,String code){
		return courseService.getSubjectHistory(AdvancedUtil.converAdvancedList(param), edu, codeType, code);
	}
}
