package cn.gilight.dmm.xg.controller;

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

import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.PageUtils;
import cn.gilight.dmm.xg.service.StuLowCostService;
import cn.gilight.framework.uitl.common.ExcelUtils;

@Controller
@RequestMapping("stuLowCost")
public class StuLowCostCtol {
	@Resource
	private StuLowCostService stuLowCostService;
	@RequestMapping()
	public String init() {
		return "stuLowCost";
	}
	
	@RequestMapping("getCountByDept")
	@ResponseBody
	public Map<String, Object> getCountByDept(String schoolYear,String termCode, Integer month,String column,Boolean asc,String param){
		return stuLowCostService.getCountByDept(schoolYear, termCode,month,column,asc,AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getStandardMap")
	@ResponseBody
	public Map<String, Object> getStandardMap(String schoolYear,String termCode){
		return stuLowCostService.getStandardMap(schoolYear,termCode);
	}
	@RequestMapping("getLastMonthData")
	@ResponseBody
	public Map<String, Object> getLastMonthData(String param,String schoolYear,String termCode,Integer month){
		return stuLowCostService.getLastMonthData(AdvancedUtil.converAdvancedList(param),schoolYear,termCode,month);
	}
	
	@RequestMapping("getTermByGrade")
	@ResponseBody
	public Map<String, Object> getTermByGrade(String schoolYear, String termCode,
			String param){
		return stuLowCostService.getTermByGrade(schoolYear, termCode, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getYearByGrade")
	@ResponseBody
	public Map<String, Object> getYearByGrade(int start, int end,
			String param){
		return stuLowCostService.getYearByGrade(start,end, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getTermBySex")
	@ResponseBody
	public Map<String, Object> getTermBySex(String schoolYear, String termCode,
			String param){
		return stuLowCostService.getTermBySex(schoolYear, termCode,AdvancedUtil.converAdvancedList(param));
	}

	@RequestMapping("getYearBySex")
	@ResponseBody
	public Map<String, Object> getYearBySex(int start, int end,
			String param){
		return stuLowCostService.getYearBySex(start, end, AdvancedUtil.converAdvancedList(param));
	}

	@RequestMapping("getTermBySubsidy")
	@ResponseBody
	public Map<String, Object> getTermBySubsidy(String schoolYear, String termCode,
			String param){
		return stuLowCostService.getTermBySubsidy(schoolYear, termCode, AdvancedUtil.converAdvancedList(param));
	}

	@RequestMapping("getYearBySubsidy")
	@ResponseBody
	public Map<String, Object> getYearBySubsidy(int start, int end,
			String param){
		return stuLowCostService.getYearBySubsidy(start, end, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getTermByLoan")
	@ResponseBody
	public Map<String, Object> getTermByLoan(String schoolYear, String termCode,
			String param){
		return stuLowCostService.getTermByLoan(schoolYear, termCode, AdvancedUtil.converAdvancedList(param));
	}

	@RequestMapping("getYearByLoan")
	@ResponseBody
	public Map<String, Object> getYearByLoan(int start, int end,
			String param){
		return stuLowCostService.getYearByLoan(start, end, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getTermByJm")
	@ResponseBody
	public Map<String, Object> getTermByJm(String schoolYear, String termCode,
			String param){
		return stuLowCostService.getTermByJm(schoolYear, termCode, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getYearByJm")
	@ResponseBody
	public Map<String, Object> getYearByJm(int start, int end,
			String param){
		return stuLowCostService.getYearByJm(start, end, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getGradeHistory")
	@ResponseBody
	public Map<String, Object> getGradeHistory(String param){
		return stuLowCostService.getGradeHistory(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getSexHistory")
	@ResponseBody
	public Map<String, Object> getSexHistory(String param){
		return stuLowCostService.getSexHistory(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getSubsidyHistory")
	@ResponseBody
	public Map<String, Object> getSubsidyHistory(String param){
		return stuLowCostService.getSubsidyHistory(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getLoanHistory")
	@ResponseBody
	public Map<String, Object> getLoanHistory(String param){
		return stuLowCostService.getLoanHistory(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getJmHistory")
	@ResponseBody
	public Map<String, Object> getJmHistory(String param){
		return stuLowCostService.getJmHistory(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getMealHistory")
	@ResponseBody
	public Map<String, Object> getMealHistory(String param){
		return stuLowCostService.getMealHistory(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getYearByMeal")
	@ResponseBody
	public Map<String, Object> getYearByMeal(int start, int end,
			String param){
		return stuLowCostService.getYearByMeal(start,end,AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getTermByMeal")
	@ResponseBody
	public Map<String, Object> getTermByMeal(String schoolYear, String termCode,
			String param){
		return stuLowCostService.getTermByMeal(schoolYear,termCode,AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getTermCountDept")
	@ResponseBody
	public Map<String, Object> getTermCountDept(String schoolYear, String termCode,
			String param){
		return stuLowCostService.getTermCountDept(schoolYear,termCode,AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getYearCountDept")
	@ResponseBody
	public Map<String, Object> getYearCountDept(int start, int end,
			String param){
		return stuLowCostService.getYearCountDept(start,end,AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("exportData")
	@ResponseBody
	public void exportData(String mc,String schoolYear, String termCode,Integer month,String pid,String param,HttpServletResponse response){
		stuLowCostService.exportData(mc,schoolYear,termCode,month,pid,AdvancedUtil.converAdvancedList(param),response);
	}
	@RequestMapping("getExportData")
	@ResponseBody
	public  Map<String, Object> getExportData(String sendType,String mc,String schoolYear, String termCode,Integer month,String pid,String param,HttpServletRequest request){
		return stuLowCostService.getExportData(sendType,mc,schoolYear,termCode,month,pid,AdvancedUtil.converAdvancedList(param),request);
	}
	
	@RequestMapping("excelAll")
	@ResponseBody
	public void excelAll(HttpServletRequest request,HttpServletResponse response) {
		stuLowCostService.excelAll(request,response);
	}
	
	@RequestMapping("sendAll")
	@ResponseBody
	public Map<String, Object> sendAll(HttpServletRequest request) {
		return stuLowCostService.sendAll(request);
	}
	
	@RequestMapping("getNearLv")
	@ResponseBody
	public Map<String, Object> getNearLv(String xnxqList,String monthList, String deptid, String param) {
		return stuLowCostService.getNearLv(xnxqList,monthList,deptid,AdvancedUtil.converAdvancedList(param));
	}
}
