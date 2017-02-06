package cn.gilight.dmm.xg.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.xg.service.ChangeService;

/**
 * 学生工作者
 * 
 * @author xuebl
 * @date 2016年4月18日 下午5:10:48
 */
@Controller
@RequestMapping("change")
public class ChangeCtol {
	
	@Resource
	private ChangeService changeService;
	
	@RequestMapping()
	public String init(){
		return "change";
	}

	@RequestMapping("getStuChangeAbstract")
	@ResponseBody
	public Map<String, Object> getStuChangeAbstract(String param){
		return changeService.getStuChangeAbstract(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getStuChange")
	@ResponseBody
	public Map<String, Object> getStuChange(String param){
		return changeService.getStuChange(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getDeptStuChange")
	@ResponseBody
	public Map<String, Object> getDeptStuChange(String param){
		return changeService.getDeptStuChange(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getStuChangeMonth")
	@ResponseBody
	public Map<String, Object> getStuChangeMonth(String param){
		return changeService.getStuChangeMonth(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getStuChangeYear")
	@ResponseBody
	public Map<String, Object> getStuChangeYear(String param){
		return changeService.getStuChangeYear(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getStuChangeHistory")
	@ResponseBody
	public Map<String, Object> getStuChangeHistory(String param){
		return changeService.getStuChangeHistory(AdvancedUtil.converAdvancedList(param));
	}
	@RequestMapping("isAll")
	@ResponseBody
	public boolean isAll(String param){
		return changeService.isAll(AdvancedUtil.converAdvancedList(param));
	}
	@RequestMapping("getStuChangeByDeptOrMajor")
	@ResponseBody
	public Map<String,Object> getStuChangeByDeptOrMajor(String tag,String param){
		return changeService.getStuChangeByDeptOrMajor(AdvancedUtil.converAdvancedList(param),tag);
	}
	
}
