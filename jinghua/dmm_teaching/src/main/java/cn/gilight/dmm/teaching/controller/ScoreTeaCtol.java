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
import cn.gilight.dmm.teaching.service.ScoreTeaService;

@Controller
@RequestMapping("scoreTea")
public class ScoreTeaCtol {

	@Resource
	private ScoreTeaService scoreTeaService;
	
	@RequestMapping()
	public String init(){
		return "scoreTea";
	}
	

	@RequestMapping("getEduList")
	@ResponseBody
	public List<Map<String, Object>> getEduList(){
		return scoreTeaService.getEduList();
	}
	
	@RequestMapping("getThList")
	@ResponseBody
	public List<Map<String, Object>> getThList(){
		return scoreTeaService.getThList();
	}
	
	@RequestMapping("getFthList")
	@ResponseBody
	public List<Map<String, Object>> getFthList(){
		return scoreTeaService.getFthList();
	}
	
	@RequestMapping("getCourseList")
	@ResponseBody
	public List<Map<String, Object>> getCourseList(String param,String schoolYear, String termCode,String edu){
		return scoreTeaService.getCourseList(AdvancedUtil.converAdvancedList(param),schoolYear,termCode,edu);
	}
	
	@RequestMapping("getTeachClassAndStuCount")
	@ResponseBody
	public Map<String, Object> getTeachClassAndStuCount(String schoolYear,String termCode, String courseId,String edu,String param){
		return scoreTeaService.getTeachClassAndStuCount(schoolYear,termCode,courseId,edu,AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getCourseNatureList")
	@ResponseBody
	public List<Map<String, Object>> getCourseNatureList(String schoolYear,String termCode, String courseId,String edu,String param){
		return scoreTeaService.getCourseNatureList(schoolYear,termCode,courseId,edu,AdvancedUtil.converAdvancedList(param));
	}
	
	
	@RequestMapping("getTimeList")
	@ResponseBody
	public List<Map<String, Object>> getTimeList(){
		return scoreTeaService.getTimeList();
	}
	
	@RequestMapping("getClassScoreGk")
	@ResponseBody
	public Map<String, Object> getClassScoreGk(String schoolYear, String termCode,
			String courseId, String nature, String edu,String params){
		return scoreTeaService.getClassScoreGk(schoolYear,termCode,courseId,nature,edu,
				AdvancedUtil.converAdvancedList(params));
	}
	
	@RequestMapping("getDyData")
	@ResponseBody
	public Map<String, Object> getDyData(String schoolYear, String termCode,
			String courseId, String classid,String nature, String edu,String params){
		return scoreTeaService.getDyData(schoolYear,termCode,courseId,classid,nature,edu,
				AdvancedUtil.converAdvancedList(params));
	}
	
	
	@RequestMapping("getSameData")
	@ResponseBody
	public Map<String, Object> getSameData(String schoolYear, 
			String courseId, String nature, String edu,String params){
		return scoreTeaService.getSameData(schoolYear,courseId,nature,edu,
				AdvancedUtil.converAdvancedList(params));
	}
	
	@RequestMapping("getChartData")
	@ResponseBody
	public Map<String, Object> getChartData(String schoolYear, String termCode,
			String courseId, String edu,String params){
		return scoreTeaService.getChartData(schoolYear,termCode,courseId,edu,
				AdvancedUtil.converAdvancedList(params));
	}
	
	@RequestMapping("getReport")
	@ResponseBody
	public List<Map<String, Object>> getReport(String schoolYear, String termCode,
			String courseId, String nature, String edu,String params){
		return scoreTeaService.getReport(schoolYear,termCode,courseId,nature,edu,
				AdvancedUtil.converAdvancedList(params));
	}
	
	@RequestMapping("saveYzZt")
	@ResponseBody
	public Map<String,Object> saveYzZt(String schoolYear, String termCode,
			String courseId, String nature, String edu,String classid){
		 scoreTeaService.saveYzZt(schoolYear,termCode,courseId,nature,classid);
		 return new HashMap<String, Object>();
	}
	
	@RequestMapping("getYzZt")
	@ResponseBody
	public Boolean getYzZt(String schoolYear, String termCode,
			String courseId, String nature, String classid){
		return  scoreTeaService.getYzZt(schoolYear,termCode,courseId,nature,classid);
	}
	
	@RequestMapping("saveDt")
	@ResponseBody
	public Map<String,Object> saveDt(String schoolYear, String termCode,
			String courseId, String nature, String edu,String classid,String kslx, String kcxx,
			String one, String two, String three, String four, String five,String fxr,String zr,String sj,String qt){
		 scoreTeaService.saveDt(schoolYear, termCode, courseId, nature, classid, kslx, kcxx, one, two, three, four, five,fxr,zr,sj,qt);
		 return new HashMap<String, Object>();
	}
	
	@RequestMapping("saveXt")
	@ResponseBody
	public Map<String,Object> saveXt(String schoolYear, String termCode,
			String courseId, String nature, String classid,
			String tx, String tf, String df,String th,String thn){
		 scoreTeaService.saveXt(schoolYear, termCode, courseId, nature, classid, JSONObject.parseArray(tx, HashMap.class),
				 JSONObject.parseArray(tf, HashMap.class) ,JSONObject.parseArray(df, HashMap.class), JSONObject.parseArray(th, String.class),JSONObject.parseArray(thn, String.class));
		 return new HashMap<String, Object>();
	}
	
	
	@RequestMapping("getDtXt")
	@ResponseBody
	public Map<String,Object> getDtXt(String schoolYear,String termCode,String course,String nature,String classid){
		return  scoreTeaService.getDtXt(schoolYear,termCode,course,nature,classid);
	}
}
