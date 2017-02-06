package cn.gilight.dmm.xg.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.xg.service.StuHighCostService;

@Controller
@RequestMapping("stuHighCost")
public class StuHighCostCtol {
	@Resource
	private StuHighCostService stuHighCostService;
	@RequestMapping()
	public String init() {
		return "stuHighCost";
	}

	@RequestMapping("getDataEndDate")
	@ResponseBody
	public Map<String, Object> getDataEndDate(){
		return stuHighCostService.getDataEndDate();
	}
	
	@RequestMapping("getStandardMap")
	@ResponseBody
	public Map<String, Object> getStandardMap(String schoolYear,String termCode){
		return stuHighCostService.getStandardMap(schoolYear,termCode);
	}
	@RequestMapping("getCountByDept")
	@ResponseBody
	public Map<String, Object> getCountByDept(String schoolYear,String termCode, Integer month,String column,Boolean asc,String param){
		return stuHighCostService.getCountByDept(schoolYear, termCode,month,column,asc,AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getLastMonthData")
	@ResponseBody
	public Map<String, Object> getLastMonthData(String param,String schoolYear,String termCode,Integer month){
		return stuHighCostService.getLastMonthData(AdvancedUtil.converAdvancedList(param),schoolYear,termCode,month);
	}
	
	@RequestMapping("getTermByGrade")
	@ResponseBody
	public Map<String, Object> getTermByGrade(String schoolYear, String termCode,
			String param){
		return stuHighCostService.getTermByGrade(schoolYear, termCode, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getYearByGrade")
	@ResponseBody
	public Map<String, Object> getYearByGrade(int start, int end,
			String param){
		return stuHighCostService.getYearByGrade(start,end, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getTermBySex")
	@ResponseBody
	public Map<String, Object> getTermBySex(String schoolYear, String termCode,
			String param){
		return stuHighCostService.getTermBySex(schoolYear, termCode,AdvancedUtil.converAdvancedList(param));
	}

	@RequestMapping("getYearBySex")
	@ResponseBody
	public Map<String, Object> getYearBySex(int start, int end,
			String param){
		return stuHighCostService.getYearBySex(start, end, AdvancedUtil.converAdvancedList(param));
	}

	@RequestMapping("getTermBySubsidy")
	@ResponseBody
	public Map<String, Object> getTermBySubsidy(String schoolYear, String termCode,
			String param){
		return stuHighCostService.getTermBySubsidy(schoolYear, termCode, AdvancedUtil.converAdvancedList(param));
	}

	@RequestMapping("getYearBySubsidy")
	@ResponseBody
	public Map<String, Object> getYearBySubsidy(int start, int end,
			String param){
		return stuHighCostService.getYearBySubsidy(start, end, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getTermByLoan")
	@ResponseBody
	public Map<String, Object> getTermByLoan(String schoolYear, String termCode,
			String param){
		return stuHighCostService.getTermByLoan(schoolYear, termCode, AdvancedUtil.converAdvancedList(param));
	}

	@RequestMapping("getYearByLoan")
	@ResponseBody
	public Map<String, Object> getYearByLoan(int start, int end,
			String param){
		return stuHighCostService.getYearByLoan(start, end, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getTermByJm")
	@ResponseBody
	public Map<String, Object> getTermByJm(String schoolYear, String termCode,
			String param){
		return stuHighCostService.getTermByJm(schoolYear, termCode, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getYearByJm")
	@ResponseBody
	public Map<String, Object> getYearByJm(int start, int end,
			String param){
		return stuHighCostService.getYearByJm(start, end, AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getGradeHistory")
	@ResponseBody
	public Map<String, Object> getGradeHistory(String param){
		return stuHighCostService.getGradeHistory(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getSexHistory")
	@ResponseBody
	public Map<String, Object> getSexHistory(String param){
		return stuHighCostService.getSexHistory(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getSubsidyHistory")
	@ResponseBody
	public Map<String, Object> getSubsidyHistory(String param){
		return stuHighCostService.getSubsidyHistory(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getLoanHistory")
	@ResponseBody
	public Map<String, Object> getLoanHistory(String param){
		return stuHighCostService.getLoanHistory(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getJmHistory")
	@ResponseBody
	public Map<String, Object> getJmHistory(String param){
		return stuHighCostService.getJmHistory(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getMealHistory")
	@ResponseBody
	public Map<String, Object> getMealHistory(String param){
		return stuHighCostService.getMealHistory(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getYearByMeal")
	@ResponseBody
	public Map<String, Object> getYearByMeal(int start, int end,
			String param){
		return stuHighCostService.getYearByMeal(start,end,AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getTermByMeal")
	@ResponseBody
	public Map<String, Object> getTermByMeal(String schoolYear, String termCode,
			String param){
		return stuHighCostService.getTermByMeal(schoolYear,termCode,AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getTermCountDept")
	@ResponseBody
	public Map<String, Object> getTermCountDept(String schoolYear, String termCode,
			String param){
		return stuHighCostService.getTermCountDept(schoolYear,termCode,AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getYearCountDept")
	@ResponseBody
	public Map<String, Object> getYearCountDept(int start, int end,
			String param){
		return stuHighCostService.getYearCountDept(start,end,AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("exportData")
	@ResponseBody
	public void exportData(String mc,String schoolYear, String termCode,Integer month,String pid,String param,HttpServletResponse response){
		 stuHighCostService.exportData(mc,schoolYear,termCode,month,pid,AdvancedUtil.converAdvancedList(param),response);
	}
	
	@RequestMapping("getExportData")
	@ResponseBody
	public  Map<String, Object> getExportData(String sendType,String mc,String schoolYear, String termCode,Integer month,String pid,String param,HttpServletRequest request){
		return stuHighCostService.getExportData(sendType,mc,schoolYear,termCode,month,pid,AdvancedUtil.converAdvancedList(param),request);
	}
	
	@RequestMapping("excelAll")
	@ResponseBody
	public void excelAll(HttpServletRequest request,HttpServletResponse response) {
		 stuHighCostService.excelAll(request,response);
	}
	
	@RequestMapping("sendAll")
	@ResponseBody
	public Map<String, Object> sendAll(HttpServletRequest request) {
		return stuHighCostService.sendAll(request);
	}
	
	@RequestMapping("getNearLv")
	@ResponseBody
	public Map<String, Object> getNearLv(String xnxqList,String monthList, String deptid, String param) {
		return stuHighCostService.getNearLv(xnxqList,monthList,deptid,AdvancedUtil.converAdvancedList(param));
	}
	
}
