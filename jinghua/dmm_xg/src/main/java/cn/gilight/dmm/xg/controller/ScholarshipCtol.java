package cn.gilight.dmm.xg.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.xg.service.ScholarshipService;

/**
 * 奖学金
 * 
 * @author xuebl
 * @date 2016年5月12日 下午4:58:10
 */
@Controller
@RequestMapping("scholarship")
public class ScholarshipCtol {

	@Resource
	private ScholarshipService scholarshipService;
	
	@RequestMapping()
	public String init(){
		return "scholarship";
	}
	
	@RequestMapping("getBzdm")
	@ResponseBody
	public Map<String, Object> getBzdm(){
		return scholarshipService.getBzdm();
	}

	@RequestMapping("getAbstract")
	@ResponseBody
	public Map<String, Object> getAbstract(String schoolYear, String edu, String param){
		return scholarshipService.getAbstract(schoolYear, edu, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getTypeList")
	@ResponseBody
	public Map<String, Object> getTypeList(String schoolYear, String edu, String param){
		return scholarshipService.queryTypeList(schoolYear, edu, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("queryDeptDataList")
	@ResponseBody
	public Map<String, Object> queryDeptDataList(String schoolYear, String edu, String param){
		return scholarshipService.queryDeptDataList(schoolYear, edu, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getBehavior")
	@ResponseBody
	public Map<String, Object> getBehavior(String schoolYear, String edu, String param){
		return scholarshipService.getBehavior(schoolYear, edu, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getCoverageGrade")
	@ResponseBody
	public Map<String, Object> getCoverageGrade(String schoolYear, String edu, String param){
		return scholarshipService.getCoverageGrade(schoolYear, edu, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getCoverageDept")
	@ResponseBody
	public Map<String, Object> getCoverageDept(String schoolYear, String edu, String param){
		return scholarshipService.getCoverageDept(schoolYear, edu, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getCoverageGradeSex")
	@ResponseBody
	public Map<String, Object> getCoverageGradeSex(String schoolYear, String edu, String param){
		return scholarshipService.getCoverageGradeSex(schoolYear, edu, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getTop")
	@ResponseBody
	public Map<String, Object> getTop(String schoolYear, String edu, String param, String column, Integer index){
		return scholarshipService.getTop(schoolYear, edu, AdvancedUtil.converAdvancedList(param), column, index);
	}
	
	@RequestMapping("getHistory")
	@ResponseBody
	public Map<String, Object> getHistory(String schoolYear, String edu, String param){
		return scholarshipService.getHistory(schoolYear, edu, AdvancedUtil.converAdvancedList(param));
	}
	
}
