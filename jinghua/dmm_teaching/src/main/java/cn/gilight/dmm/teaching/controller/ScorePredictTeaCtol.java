package cn.gilight.dmm.teaching.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.PageUtils;
import cn.gilight.dmm.teaching.service.ScorePredictTeaService;
import cn.gilight.framework.uitl.common.ExcelUtils;


@Controller
@RequestMapping("scorePredictTea")
public class ScorePredictTeaCtol {
	@Resource
	private ScorePredictTeaService scorePredictTeaService;
	
	@RequestMapping()
	public String init(){
		return "scorePredictTea";
	}
	
	@RequestMapping("getEduList")
	@ResponseBody
	public List<Map<String, Object>> getEduList(){
		return scorePredictTeaService.getEduList();
	}
	
	@RequestMapping("getDate")
	@ResponseBody
	public Map<String, Object> getDate(){
		return scorePredictTeaService.getDate();
	}
	
	@RequestMapping("getTimeList")
	@ResponseBody
	public List<Map<String, Object>> getTimeList(){
		return scorePredictTeaService.getTimeList();
	}
	
	@RequestMapping("getThList")
	@ResponseBody
	public List<Map<String, Object>> getThList(){
		return scorePredictTeaService.getThList();
	}
	
	@RequestMapping("getFthList")
	@ResponseBody
	public List<Map<String, Object>> getFthList(){
		return scorePredictTeaService.getFthList();
	}
	
	@RequestMapping("getCourseList")
	@ResponseBody
	public List<Map<String, Object>> getCourseList(String param,String schoolYear, String termCode,String edu){
		return scorePredictTeaService.getCourseList(AdvancedUtil.converAdvancedList(param),schoolYear,termCode,edu);
	}
	
	@RequestMapping("getTeachClassAndStuCount")
	@ResponseBody
	public Map<String, Object> getTeachClassAndStuCount(String schoolYear,String termCode, String courseId,String edu,String param){
		return scorePredictTeaService.getTeachClassAndStuCount(schoolYear,termCode,courseId,edu,AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getCourseNatureList")
	@ResponseBody
	public List<Map<String, Object>> getCourseNatureList(String schoolYear,String termCode, String courseId,String edu,String param){
		return scorePredictTeaService.getCourseNatureList(schoolYear,termCode,courseId,edu,AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getClassScoreGk")
	@ResponseBody
	public Map<String, Object> getClassScoreGk(String schoolYear,String termCode, String courseId, String nature,String edu,String param){
		return scorePredictTeaService.getClassScoreGk(schoolYear,termCode,courseId,nature,edu,AdvancedUtil.converAdvancedList(param));
	}	
	
	@RequestMapping("getChartData")
	@ResponseBody
	public Map<String, Object> getChartData(String schoolYear,String termCode, String courseId,String edu,String param){
		return scorePredictTeaService.getChartData(schoolYear,termCode,courseId,edu,AdvancedUtil.converAdvancedList(param));
	}	
	
	@RequestMapping("getSameData")
	@ResponseBody
	public Map<String, Object> getSameData(String schoolYear, String courseId,String nature,String edu,String param){
		return scorePredictTeaService.getSameData(schoolYear,courseId,nature,edu,AdvancedUtil.converAdvancedList(param));
	}	
	
	@RequestMapping("getPrecisionList")
	@ResponseBody
	public Map<String, Object> getPrecisionList(String schoolYear, String courseId,String nature,String edu,String param){
		return scorePredictTeaService.getPrecisionList(schoolYear,courseId,nature,edu,AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getNowPrecision")
	@ResponseBody
	public Map<String, Object> getNowPrecision(String schoolYear,String termCode,String courseId,String nature,String edu,String param){
		return scorePredictTeaService.getNowPrecision(schoolYear,termCode,courseId,nature,edu,AdvancedUtil.converAdvancedList(param));
	}
}
