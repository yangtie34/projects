package cn.gilight.dmm.teaching.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.teaching.service.BysScoreService;

@Controller
@RequestMapping("bysScore")
public class BysScoreCtol {
	@Resource
	private BysScoreService bysScoreService;
	
	@RequestMapping()
	public String init(){
		return "bysScore";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getScoreTest")
	@ResponseBody
	public Map<String, Object> getScoreTest(String values,String param){
		return bysScoreService.getScoreTest(JSONObject.parseObject(values, HashMap.class),AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("/getEduList")
	@ResponseBody
	public Map<String, Object> getEduList(){
		return bysScoreService.getEduList();
	}
	
	@RequestMapping("/getScoreGroup")
	@ResponseBody
	public List<Map<String, Object>> getScoreGroup(){
		return bysScoreService.getScoreGroup();
	}
	
	@RequestMapping("/getPeriodList")
	@ResponseBody
	public Map<String, Object> getPeriodList(){
		return bysScoreService.getPeriodList();
	}
	
	@RequestMapping("/getDateList")
	@ResponseBody
	public Map<String, Object> getDateList(){
		return bysScoreService.getDateList();
	}
	
	@RequestMapping("/getTargetList")
	@ResponseBody
	public Map<String, Object> getTargetList(){
		return bysScoreService.getTargetList();
	}
	
	@RequestMapping("/getScoreTypeList")
	@ResponseBody
	public Map<String, Object> getScoreTypeList(){
		return bysScoreService.getScoreTypeList();
	}
	
	@RequestMapping("/getXzList")
	@ResponseBody
	public Map<String, Object> getXzList(String param,String edu){
		return bysScoreService.getXzList(AdvancedUtil.converAdvancedList(param),edu);
	}
	
	@RequestMapping("/getScoreLine")
	@ResponseBody
	public Map<String, Object> getScoreLine(String param,String edu, String date, Integer xz, String scoreType, String target){
		return bysScoreService.getScoreLine(AdvancedUtil.converAdvancedList(param),edu,date,xz,scoreType,target);
	}
	
	@RequestMapping("/getScoreFb")
	@ResponseBody
	public Map<String, Object> getScoreFb(String param,Integer period, Integer xz, String edu){
		return bysScoreService.getScoreFb(AdvancedUtil.converAdvancedList(param),period,xz,edu);
	}
	
}
