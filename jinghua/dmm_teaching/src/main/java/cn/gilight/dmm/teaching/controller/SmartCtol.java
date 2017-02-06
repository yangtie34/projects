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
import cn.gilight.dmm.teaching.service.SmartService;
import cn.gilight.framework.uitl.common.ExcelUtils;

/**
 * 学霸
 * @author hanpl
 * @date 2016年6月28日 14:08
 */
@Controller
@RequestMapping("smart")
public class SmartCtol {
	@Resource
	private SmartService smartService;
	
	@RequestMapping()
	public String init(){
		return "smart";
	}
	
	@RequestMapping("getGradeSelect")
	@ResponseBody
	public Map<String, Object> getGradeSelect(String param,Integer schoolYear){
		return smartService.getGradeSelect(AdvancedUtil.converAdvancedList(param),schoolYear);
	}
	
	@RequestMapping("getYearAndTerm")
	@ResponseBody
	public Map<String, Object> getYearAndTerm(){
		return smartService.getYearAndTerm();
	}
	
	@RequestMapping("getEduSelect")
	@ResponseBody
	public Map<String, Object> getEduSelect(){
		return smartService.getEduSelect();
	}
	
	@RequestMapping("getTopGpa")
	@ResponseBody
	public Map<String, Object> getTopGpa(Integer schoolYear, String term,
			String grade, String edu,String param,int pagesize,int index){
		return smartService.getTopGpa(schoolYear,term,grade,edu,AdvancedUtil.converAdvancedList(param),pagesize,index);
	}
	
	@RequestMapping("getXbFrom")
	@ResponseBody
	public Map<String, Object> getXbFrom(Integer schoolYear, String term,
			String grade, String edu, String xzqh, Boolean updown,String param){
		return smartService.getXbFrom(schoolYear,term,grade,edu,xzqh,updown,AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getTable")
	@ResponseBody
	public Map<String, Object> getTable(Integer schoolYear, String term,
			String grade, String edu,int pagesize,int index,String column,Boolean asc,String type,String param){
		return smartService.getTable(schoolYear,term,grade,edu,pagesize,index,column,asc,type,AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getXbCountLine")
	@ResponseBody
	public Map<String, Object> getXbCountLine(String edu,String param){
		return smartService.getXbCountLine(edu,AdvancedUtil.converAdvancedList(param));
	}
	
	
	@RequestMapping("getRadar")
	@ResponseBody
	public Map<String, Object> getRadar(Integer schoolYear, String term,
			String grade, String edu,String param){
		return smartService.getRadar(schoolYear,term,grade,edu,AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getDisplayedLevelType")
	@ResponseBody
	public Map<String,Object> getDisplayedLevelType(){
		return smartService.getDisplayedLevelType();
	}
	
	@RequestMapping("getTimeLine")
	@ResponseBody
	public Map<String, Object> getTimeLine(Integer schoolYear,String termCode){
		return smartService.getTimeLine(schoolYear,termCode);
	}
	
	@RequestMapping("getRadarStu")
	@ResponseBody
	public Map<String, Object> getRadarStu(Integer schoolYear, String term,
			String grade, String edu, String stuNo,String param){
		return smartService.getRadarStu(schoolYear, term, grade, edu, stuNo, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getDormStu")
	@ResponseBody
	public Map<String, Object> getDormStu(String stuNo){
		return smartService.getDormStu(stuNo);
	}
	
	@RequestMapping("getCostStu")
	@ResponseBody
	public Map<String, Object> getCostStu(Integer schoolYear, String term,
			String grade, String edu, String stuNo,String param){
		return smartService.getCostStu(schoolYear, term, grade, edu, stuNo, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getBorrowStu")
	@ResponseBody
	public Map<String, Object> getBorrowStu(Integer schoolYear, String term,
			String grade, String edu, String stuNo,String param){
		return smartService.getBorrowStu(schoolYear, term, grade, edu, stuNo, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getScoreStu")
	@ResponseBody
	public Map<String, Object> getScoreStu(Integer schoolYear, String term,
			String grade, String edu, String stuNo,String param){
		return smartService.getScoreStu(schoolYear, term, grade, edu, stuNo, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getScoreStuMx")
	@ResponseBody
	public Map<String, Object> getScoreStuMx(Integer schoolYear, String term,String stuNo){
		return smartService.getScoreStuMx(schoolYear, term, stuNo);
	}
}
