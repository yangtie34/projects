package cn.gilight.dmm.xg.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.xg.service.PunishService;

/**
 * 违纪处分
 * 
 * @author xuebl
 * @date 2016年5月10日 下午6:08:48
 */
@Controller
@RequestMapping("punish")
public class PunishCtol {

	@Resource
	private PunishService punishService;
	
	@RequestMapping()
	public String init(){
		return "punish";
	}

	@RequestMapping("getBzdm")
	@ResponseBody
	public Map<String, Object> getBzdm(){
		return punishService.getBzdm();
	}

	@RequestMapping("getAbstract")
	@ResponseBody
	public Map<String, Object> getAbstract(String start_year, String end_year, String edu, String param){
		return punishService.getAbstract(start_year, end_year, edu, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getDistribution")
	@ResponseBody
	public Map<String, Object> getDistribution(String start_year, String end_year, String edu, String param){
		return punishService.getDistribution(start_year, end_year, edu, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getDeptDataList")
	@ResponseBody
	public Map<String, Object> getDeptDataList(String start_year, String end_year, String edu, String param){
		return punishService.getDeptPersonTimeList(start_year, end_year, edu, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getDeptCountScaleList")
	@ResponseBody
	public Map<String, Object> getDeptCountScaleList(String start_year, String end_year, String edu, String param){
		return punishService.getDeptCountScaleList(start_year, end_year, edu, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getMonthList")
	@ResponseBody
	public Map<String, Object> getMonthList(String start_year, String end_year, String edu, String param){
		return punishService.getMonthList(start_year, end_year, edu, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getGrade")
	@ResponseBody
	public Map<String, Object> getGrade(String start_year, String end_year, String edu, String param){
		return punishService.getGrade(start_year, end_year, edu, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getAge")
	@ResponseBody
	public Map<String, Object> getAge(String start_year, String end_year, String edu, String param){
		return punishService.getAge(start_year, end_year, edu, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getPunishAgain")
	@ResponseBody
	public List<Map<String, Object>> getPunishAgain(String edu, String param){
		return punishService.getPunishAgain(edu, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getLiForAdd")
	@ResponseBody
	public Map<String, Object> getLiForAdd(String start_year, String end_year, String edu, String param){
		return punishService.getLiForAdd(start_year, end_year, edu, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getSexAndGradeAndOther")
	@ResponseBody
	public Map<String, Object> getSexAndGradeAndOther(String start_year, String end_year, String edu,String tag, String param){
		return punishService.getSexAndGradeAndOther(start_year, end_year, edu,tag, AdvancedUtil.converAdvancedList(param));
	}
	
}
