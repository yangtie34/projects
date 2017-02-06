package cn.gilight.dmm.teaching.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.teaching.service.ScoreService;

/**
 * 学生成绩
 * 
 * @author xuebl
 * @date 2016年6月22日 下午12:08:44
 */
@Controller
@RequestMapping("score")
public class ScoreCtol {

	@Resource
	private ScoreService scoreService;
	
	@RequestMapping()
	public String init(){
		return "score";
	}
	
	@RequestMapping("getBzdm")
	@ResponseBody
	public Map<String, Object> getBzdm(){
		return scoreService.getBzdm();
	}

	@RequestMapping("getAbstract")
	@ResponseBody
	public Map<String, Object> getAbstract(String param, String schoolYear, String termCode, String edu){
		return scoreService.getAbstract(AdvancedUtil.converAdvancedList(param), schoolYear, termCode, edu);
	}
	
	@RequestMapping("getGroup")
	@ResponseBody
	public Map<String, Object> getGroup(String param, String schoolYear, String termCode, String edu){
		return scoreService.getGroup(AdvancedUtil.converAdvancedList(param), schoolYear, termCode, edu);
	}
	
	@RequestMapping("getScale")
	@ResponseBody
	public Map<String, Object> getScale(String param, String schoolYear, String termCode, String edu){
		return scoreService.getScale(AdvancedUtil.converAdvancedList(param), schoolYear, termCode, edu);
	}
	
	@RequestMapping("getGpaCourse")
	@ResponseBody
	public Map<String, Object> getGpaCourse(String param, String schoolYear, String termCode, String edu, String type){
		return scoreService.getGpaCourse(AdvancedUtil.converAdvancedList(param), schoolYear, termCode, edu, type);
	}

	@RequestMapping("getDisplayedLevelType")
	@ResponseBody
	public Map<String, Object> getDisplayedLevelType(String param, String schoolYear){
		Map<String, Object> map = new HashMap<>();
		map.put("level_type", scoreService.getDisplayedLevelType(AdvancedUtil.converAdvancedList(param), schoolYear));
		return map;
	}

	@RequestMapping("getGridList")
	@ResponseBody
	public Map<String, Object> getGridList(String param, String schoolYear, String termCode, String edu, String tab_id, 
			String order, String asc, Integer index){
		return scoreService.getGridList(AdvancedUtil.converAdvancedList(param), schoolYear, termCode, edu, tab_id, order, asc, index);
	}
	
	@RequestMapping("getHistoryGridList")
	@ResponseBody
	public Map<String, Object> getHistoryGridList(String param, String schoolYear, String termCode, String edu, String tab_id, 
			String order, String asc, Integer index){
		return scoreService.getHistoryGridList(AdvancedUtil.converAdvancedList(param), schoolYear, termCode, edu, tab_id, order, asc, index);
	}
	
	private final static String MV_TEST = "/test";
	@RequestMapping(MV_TEST)
	public String Test(){
		DevUtils.p(" test teaching ");
		return MV_TEST;
	}
	
}
