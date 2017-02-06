package cn.gilight.dmm.xg.controller;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.xg.service.SubsidyService;

/**
 * 助学金
 * 
 * @author xuebl
 * @date 2016年5月12日 下午4:58:10
 */
@Controller
@RequestMapping("subsidy")
public class SubsidyCtol {

	@Resource
	private SubsidyService subsidyService;
	
	@RequestMapping()
	public String init(){
		return "subsidy";
	}

	@RequestMapping("getBzdm")
	@ResponseBody
	public Map<String, Object> getBzdm(){
		return subsidyService.getBzdm();
	}

	@RequestMapping("getAbstract")
	@ResponseBody
	public Map<String, Object> getAbstract(String schoolYear, String edu, String param){
		return subsidyService.getAbstract(schoolYear, edu, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getTypeList")
	@ResponseBody
	public Map<String, Object> getTypeList(String schoolYear, String edu, String param){
		return subsidyService.queryTypeList(schoolYear, edu, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("queryDeptDataList")
	@ResponseBody
	public Map<String, Object> queryDeptDataList(String schoolYear, String edu, String param){
		return subsidyService.queryDeptDataList(schoolYear, edu, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getBehavior")
	@ResponseBody
	public Map<String, Object> getBehavior(String schoolYear, String edu, String param){
		return subsidyService.getBehavior(schoolYear, edu, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getCoverageGrade")
	@ResponseBody
	public Map<String, Object> getCoverageGrade(String schoolYear, String edu, String param){
		return subsidyService.getCoverageGrade(schoolYear, edu, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getCoverageDept")
	@ResponseBody
	public Map<String, Object> getCoverageDept(String schoolYear, String edu, String param){
		return subsidyService.getCoverageDept(schoolYear, edu, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getHistory")
	@ResponseBody
	public Map<String, Object> getHistory(String schoolYear, String edu, String param){
		return subsidyService.getHistory(schoolYear, edu, AdvancedUtil.converAdvancedList(param));
	}
}
