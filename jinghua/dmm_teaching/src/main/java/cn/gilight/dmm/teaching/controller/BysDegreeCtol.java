package cn.gilight.dmm.teaching.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.teaching.service.BysDegreeService;
import cn.gilight.dmm.teaching.service.BysQxService;

@Controller
@RequestMapping("bysDegree")
public class BysDegreeCtol {
	
	@Resource
	private BysQxService bysQxService;
	@Resource
	private BysDegreeService bysDegreeService;
	
	@RequestMapping()
	public String init(){
		return "bysDegree";
	}
	
	@RequestMapping("/getTimeList")
	@ResponseBody
	public List<Map<String, Object>>  getTimeList(){
		return bysQxService.getTimeList();
	}
	
	@RequestMapping("/getThList")
	@ResponseBody
	public Map<String, Object>  getThList(){
		return bysDegreeService.getThList();
	}
	
	
	@RequestMapping("/getTopData")
	@ResponseBody
	public Map<String, Object>  getTopData(String param,String schoolYear){
		return bysDegreeService.getTopData(AdvancedUtil.converAdvancedList(param),schoolYear);
	}
	
	@RequestMapping("/getTableData")
	@ResponseBody
	public Map<String, Object>  getTableData(String param,String schoolYear,String Lx, String order, Boolean asc,int pagesize,int index){
		return bysDegreeService.getTableData(AdvancedUtil.converAdvancedList(param),schoolYear,Lx,order,asc,pagesize,index);
	}
	
	@RequestMapping("/getLvByDept")
	@ResponseBody
	public Map<String, Object>  getLvByDept(String param,String schoolYear){
		return bysDegreeService.getLvByDept(AdvancedUtil.converAdvancedList(param),schoolYear);
	}
	
	@RequestMapping("/getBysScore")
	@ResponseBody
	public Map<String, Object>  getBysScore(String param,String schoolYear, String Lx, String type){
		return bysDegreeService.getBysScore(AdvancedUtil.converAdvancedList(param),schoolYear,Lx,type);
	}
	
	@RequestMapping("/getBysGpa")
	@ResponseBody
	public Map<String, Object>  getBysGpa(String param,String schoolYear, String type){
		return bysDegreeService.getBysGpa(AdvancedUtil.converAdvancedList(param),schoolYear,type);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getBySyLvList")
	@ResponseBody
	public List<Map<String, Object>> getBySyLvList(String param,String values){
		return bysDegreeService.getBySyLvList(AdvancedUtil.converAdvancedList(param),JSONObject.parseObject(values, HashMap.class));
	}
}
