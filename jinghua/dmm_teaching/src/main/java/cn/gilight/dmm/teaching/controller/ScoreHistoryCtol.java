package cn.gilight.dmm.teaching.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.teaching.service.ScoreHistoryService;

/**
 * 学生成绩-历史变化
 * 
 * @author xuebl
 * @date 2016年6月22日 下午12:08:44
 */
@Controller
@RequestMapping("scoreHistory")
public class ScoreHistoryCtol {

	@Resource
	private ScoreHistoryService scoreHistoryService;
	
	@RequestMapping()
	public String init(){
		return "scoreHistory";
	}

	@RequestMapping("/getHistoryYear")
	@ResponseBody
	public Map<String, Object> getHistoryYear(String param){
		return scoreHistoryService.getHistoryYear(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("/getSex")
	@ResponseBody
	public Map<String, Object> getSex(String param){
		return scoreHistoryService.getSex(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("/getGrade")
	@ResponseBody
	public Map<String, Object> getGrade(String param){
		return scoreHistoryService.getGrade(AdvancedUtil.converAdvancedList(param));
	}
	
}
