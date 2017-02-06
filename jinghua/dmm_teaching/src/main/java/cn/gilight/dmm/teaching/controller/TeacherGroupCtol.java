package cn.gilight.dmm.teaching.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.teaching.service.TeacherGroupService;

/**
 * 师资力量
 * @author xuebl
 * @date 2016年6月28日 14:08
 */
@Controller
@RequestMapping("teacherGroup")
public class TeacherGroupCtol {
	
	@Resource
	private TeacherGroupService teacherGroupService;
	
	@RequestMapping()
	public String init(){
		return "teacherGroup";
	}
	
	@RequestMapping("getAbstract")
	@ResponseBody
	public Map<String, Object> getAbstract(String param){
		return teacherGroupService.getAbstract(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getDistribution")
	@ResponseBody
	public Map<String, Object> getDistribution(String param, String lx){
		return teacherGroupService.getDistribution(AdvancedUtil.converAdvancedList(param), lx);
	}
	@RequestMapping("getHistoryTechnical")
	@ResponseBody
	public Map<String, Object> getHistoryTechnical(String param, String lx){
		return teacherGroupService.getHistoryTechnical(AdvancedUtil.converAdvancedList(param), lx);
	}
	@RequestMapping("getHistoryDegree")
	@ResponseBody
	public Map<String, Object> getHistoryDegree(String param, String lx){
		return teacherGroupService.getHistoryDegree(AdvancedUtil.converAdvancedList(param), lx);
	}
	@RequestMapping("getHistoryEdu")
	@ResponseBody
	public Map<String, Object> getHistoryEdu(String param, String lx){
		return teacherGroupService.getHistoryEdu(AdvancedUtil.converAdvancedList(param), lx);
	}
	
	@RequestMapping("getSubjectGroup")
	@ResponseBody
	public Map<String, Object> getSubjectGroup(String param, String lx){
		return teacherGroupService.getSubjectGroup(AdvancedUtil.converAdvancedList(param), lx);
	}
	
	@RequestMapping("getAgeGroup")
	@ResponseBody
	public Map<String, Object> getAgeGroup(String param, String lx){
		return teacherGroupService.getAgeGroup(AdvancedUtil.converAdvancedList(param), lx);
	}
	
	@RequestMapping("getSchoolAgeGroup")
	@ResponseBody
	public Map<String, Object> getSchoolAgeGroup(String param, String lx){
		return teacherGroupService.getSchoolAgeGroup(AdvancedUtil.converAdvancedList(param), lx);
	}
	
	@RequestMapping("getDeptList")
	@ResponseBody
	public Map<String, Object> getDeptList(String param, String lx){
		return teacherGroupService.getDeptList(AdvancedUtil.converAdvancedList(param), lx);
	}
	
	@RequestMapping("getHistoryList")
	@ResponseBody
	public Map<String, Object> getHistoryList(String param, String lx){
		return teacherGroupService.getHistoryList(AdvancedUtil.converAdvancedList(param), lx);
	}
	
	@RequestMapping("getDeptScaleList")
	@ResponseBody
	public Map<String, Object> getDeptScaleList(String param){
		return teacherGroupService.getDeptScaleList(AdvancedUtil.converAdvancedList(param));
	}
	
}
