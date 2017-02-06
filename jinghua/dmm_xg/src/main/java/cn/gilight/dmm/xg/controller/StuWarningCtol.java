package cn.gilight.dmm.xg.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.xg.service.StuWarningService;

/**
 * 学生预警
 * 
 * @author xuebl
 * @date 2016年6月15日 下午3:55:38
 */
@Controller
@RequestMapping("stuWarning")
public class StuWarningCtol {


	@Resource
	private StuWarningService stuWarningService;
	
	@RequestMapping()
	public String init(){
		return "stuWarning";
	}

	@RequestMapping("getAbstract")
	@ResponseBody
	public Map<String, Object> getAbstract(String date, String param, String mold){
		return stuWarningService.getAbstract(date, AdvancedUtil.converAdvancedList(param), mold);
	}

	@RequestMapping("getDeptDataGrid")
	@ResponseBody
	public Map<String, Object> getDeptDataGrid(String date, String type, String valueType, String asc, String param, String mold){
		return stuWarningService.getDeptDataGrid(date, type, valueType, asc, AdvancedUtil.converAdvancedList(param), mold);
	}

	@RequestMapping("isTermOver")
	@ResponseBody
	public boolean isTermOver(String date){
		return stuWarningService.isTermOver(date);
	}
	
	@RequestMapping("getBzdmXnXq")
	@ResponseBody
	public Map<String, Object> getBzdmXnXq(){
		return stuWarningService.getBzdmXnXq();
	}
	
	@RequestMapping("getIsSetStartEndDate")
	@ResponseBody
	public Map<String, Object> getIsSetStartEndDate(String schoolYear, String termCode){
		return stuWarningService.getIsSetStartEndDate(schoolYear, termCode);
	}
	
	@RequestMapping("getDistribution")
	@ResponseBody
	public Map<String, Object> getDistribution(String param, String schoolYear, String termCode, String mold){
		return stuWarningService.getDistribution(AdvancedUtil.converAdvancedList(param), schoolYear, termCode, mold);
	}
	
	@RequestMapping("getDeptDataList")
	@ResponseBody
	public Map<String, Object> getDeptDataList(String param, String schoolYear, String termCode, String mold){
		return stuWarningService.getDeptDataList(AdvancedUtil.converAdvancedList(param), schoolYear, termCode, mold);
	}
	
	@RequestMapping("send")
	@ResponseBody
	public Map<String, Object> send(String deptId, String date){
		return stuWarningService.send(deptId, date);
	}
	
	@RequestMapping("getSkipClassByWeekDayOrClas")
	@ResponseBody
	public Map<String,Object> getSkipClassByWeekDayOrClas(String param, String schoolYear, String termCode, String mold){
		return stuWarningService.getSkipClassByWeekDayOrClas(AdvancedUtil.converAdvancedList(param), schoolYear, termCode, mold);
	}
}
