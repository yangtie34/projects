package cn.gilight.dmm.xg.controller;


import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.xg.service.ChangeBadService;

/**
 * 学籍不良异动
 * 
 * @author xuebl
 * @date 2016年5月10日 下午6:08:48
 */
@Controller
@RequestMapping("changeBad")
public class ChangeBadCtol {


	@Resource
	private ChangeBadService changeBadService;
	
	
	@RequestMapping()
	public String init(){
		return "changeBad";
	}

	@RequestMapping("getStuChangeAbstract")
	@ResponseBody
	public Map<String, Object> getStuChangeAbstract(String changeCode, String param){
		return changeBadService.getStuChangeAbstract(changeCode, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getChangeBadList")
	@ResponseBody
	public Map<String, Object> getChangeBadList(){
		return changeBadService.getChangeBadList();
	}

	@RequestMapping("getStuChange")
	@ResponseBody
	public Map<String, Object> getStuChange(String changeCode, String param){
		return changeBadService.getStuChange(changeCode, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getDeptStuChange")
	@ResponseBody
	public Map<String, Object> getDeptStuChange(String changeCode, String param){
		return changeBadService.getDeptStuChange(changeCode, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getStuChangeMonth")
	@ResponseBody
	public Map<String, Object> getStuChangeMonth(String changeCode, String param){
		return changeBadService.getStuChangeMonth(changeCode, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getStuChangeYear")
	@ResponseBody
	public Map<String, Object> getStuChangeYear(String changeCode, String param){
		return changeBadService.getStuChangeYear(changeCode, AdvancedUtil.converAdvancedList(param));
	}

	@RequestMapping("getStuChangeHistory")
	@ResponseBody
	public Map<String, Object> getStuChangeHistory(String changeCode, String param){
		return changeBadService.getStuChangeHistory(changeCode, AdvancedUtil.converAdvancedList(param));
	}
	@RequestMapping("getChangeAgain")
	@ResponseBody
	public Map<String, Object> getChangeAgain(String changeCode, String param){
		return changeBadService.getChangeAgain(changeCode, AdvancedUtil.converAdvancedList(param));
	}
	
}
