package cn.gilight.dmm.teaching.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.PageUtils;
import cn.gilight.dmm.teaching.service.MajorStatusService;

/**
 * 专业开设
 * 
 * @author xuebl
 * @date 2016年10月18日 下午5:19:00
 */
@Controller
@RequestMapping("majorStatus")
public class MajorStatusCtol {

	@Resource
	private MajorStatusService majorStatusService;
	
	@RequestMapping()
	public String init(){
		return "majorStatus";
	}
	
	@RequestMapping("queryXn")
	@ResponseBody
	public Map<String, Object> queryXn(){
		return majorStatusService.queryXn();
	}
	
	@RequestMapping("queryMajorScoreList")
	@ResponseBody
	public Map<String, Object> queryMajorScoreList(String param, String page, String schoolYear, String order, String asc){
		return majorStatusService.queryMajorScoreList(AdvancedUtil.converAdvancedList(param), PageUtils.converPage(page), schoolYear, order, asc);
	}
	
	@RequestMapping("queryMajorFailScaleList")
	@ResponseBody
	public Map<String, Object> queryMajorFailScaleList(String param, String page, String schoolYear, String order, String asc){
		return majorStatusService.queryMajorFailScaleList(AdvancedUtil.converAdvancedList(param), PageUtils.converPage(page), schoolYear, order, asc);
	}
	
	@RequestMapping("queryMajorEvaluateTeachList")
	@ResponseBody
	public Map<String, Object> queryMajorEvaluateTeachList(String param, String page, String schoolYear, String order, String asc){
		return majorStatusService.queryMajorEvaluateTeachList(AdvancedUtil.converAdvancedList(param), PageUtils.converPage(page), schoolYear, order, asc);
	}
	
	@RequestMapping("queryMajorByJyList")
	@ResponseBody
	public Map<String, Object> queryMajorByJyList(String param, String page, String schoolYear, String order, String asc){
		return majorStatusService.queryMajorByJyList(AdvancedUtil.converAdvancedList(param), PageUtils.converPage(page), schoolYear, order, asc);
	}
	
	@RequestMapping("queryMajorSearchList")
	@ResponseBody
	public Map<String, Object> queryMajorSearchList(String param, Integer year, String score, String failScale,
			String evaluateTeach, String by, String jy){
		return majorStatusService.queryMajorSearchList(AdvancedUtil.converAdvancedList(param), year, score, failScale, evaluateTeach, by, jy);
	}
	
	
	@RequestMapping("queryMajorScoreHis")
	@ResponseBody
	public Map<String, Object> queryMajorScoreHis(String param, String id){
		return majorStatusService.queryMajorScoreHis(AdvancedUtil.converAdvancedList(param),id);
	}
	@RequestMapping("queryMajorFailScaleHis")
	@ResponseBody
	public Map<String, Object> queryMajorFailScaleHis(String param, String id){
		return majorStatusService.queryMajorFailScaleHis(AdvancedUtil.converAdvancedList(param),id);
	}
	@RequestMapping("queryMajorEvaluateTeachHis")
	@ResponseBody
	public Map<String, Object> queryMajorEvaluateTeachHis(String param, String id){
		return majorStatusService.queryMajorEvaluateTeachHis(AdvancedUtil.converAdvancedList(param),id);
	}
	@RequestMapping("queryMajorByHis")
	@ResponseBody
	public Map<String, Object> queryMajorByHis(String param, String id){
		return majorStatusService.queryMajorByHis(AdvancedUtil.converAdvancedList(param),id);
	}
	@RequestMapping("queryMajorJyHis")
	@ResponseBody
	public Map<String, Object> queryMajorJyHis(String param, String id){
		return majorStatusService.queryMajorJyHis(AdvancedUtil.converAdvancedList(param),id);
	}
	
	
}
