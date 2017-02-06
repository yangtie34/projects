package cn.gilight.dmm.teaching.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gilight.dmm.teaching.service.TeachingFundsService;

/**
 * 教学经费
 * @author caijc
 *
 */
@Controller
@RequestMapping("teachingFunds")
public class TeachingFundsCtrl {
	@Resource
	private TeachingFundsService teachingFundsService;
	@RequestMapping()
	public String init(){
		return "teachingFunds";
	}
	
	@RequestMapping("getTeaFundsByAll")
	@ResponseBody
	public Map<String,Object> getTeaFundsByAll(int year){
		return teachingFundsService.getTeaFundsByAll(year);
	}
	
	@RequestMapping("getTeaFundsBySty")
	@ResponseBody
	public Map<String,Object> getTeaFundsBySty(int year){
		return teachingFundsService.getTeaFundsBySty(year);
	}
	
	@RequestMapping("getTeaFundsLine")
	@ResponseBody
	public Map<String,Object> getTeaFundsLine(int year){
		return teachingFundsService.getTeaFundsLine(year);
	}

	@RequestMapping("getTeaFundsByCount")
	@ResponseBody
	public Map<String,Object> getTeaFundsByCount(int year){
		return teachingFundsService.getTeaFundsByCount(year);
	}
	
	@RequestMapping("getTeaFundsByZB")
	@ResponseBody
	public Map<String,Object> getTeaFundsByZB(int year){
		return teachingFundsService.getTeaFundsByZB(year);
	}
	
	@RequestMapping("getTeaFundsAvg")
	@ResponseBody
	public Map<String,Object> getTeaFundsAvg(int year){
		return teachingFundsService.getTeaFundsAvg(year);
	}
	
	@RequestMapping("getTeaFundsByDept")
	@ResponseBody
	public Map<String,Object> getTeaFundsByDept(int year){
		return teachingFundsService.getTeaFundsByDept(year);
	}
	
	@RequestMapping("getTeaFundsYear")
	@ResponseBody
	public Map<String,Object> getTeaFundsYear(){
		return teachingFundsService.getTeaFundsYear();
	}
	
}
