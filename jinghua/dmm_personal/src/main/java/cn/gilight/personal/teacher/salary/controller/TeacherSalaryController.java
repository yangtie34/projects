package cn.gilight.personal.teacher.salary.controller;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhnu.syspermiss.util.ContextHolderUtils;
import cn.gilight.personal.teacher.main.controller.TeacherMainController;
import cn.gilight.personal.teacher.salary.service.TeacherSalaryService;

@Controller("teacherSalaryController")
@RequestMapping("/teacher/salary")
public class TeacherSalaryController {
	private Logger log = Logger.getLogger(TeacherMainController.class);
	
	@Autowired
	private TeacherSalaryService teacherSalaryService;
	
	@RequestMapping("/lastSalary")
	@ResponseBody
	public Map<String,Object> getLastSalary(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return teacherSalaryService.getLastSalary(username);
	}
	
	@RequestMapping("/totalSalary")
	@ResponseBody
	public Map<String,Object> getTotalSalary(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return teacherSalaryService.getTotalSalary(username);
	}
	
	@RequestMapping("/salaryCompose")
	@ResponseBody
	public List<Map<String,Object>> getSalaryCompose(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return teacherSalaryService.getSalaryCompose(username);
	}
	
	@RequestMapping("/fiveYearSalary")
	@ResponseBody
	public List<Map<String,Object>> getFiveYearSalary(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return teacherSalaryService.getFiveYearSalary(username);
	}
	
	@RequestMapping("/historySalary")
	@ResponseBody
	public List<Map<String,Object>> getHistorySalary(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return teacherSalaryService.getHistorySalary(username);
	}
	
	@RequestMapping("/retireTotalSalary")
	@ResponseBody
	public Map<String,Object> getRetireTotalSalary(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return teacherSalaryService.getRetireTotalSalary(username);
	}
	
	@RequestMapping("/lastSalaryCom")
	@ResponseBody
	public Map<String,Object> getLastSalaryCom(String year_,String month_){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return teacherSalaryService.getLastSalaryCom(username,year_,month_);
	}
	
	@RequestMapping("/lastSalaryPayable")
	@ResponseBody
	public List<Map<String,Object>> getLastSalaryPayable(String year_,String month_){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return teacherSalaryService.getLastSalaryPayable(username,year_,month_);
	}
	
	@RequestMapping("/lastSalaryTotal")
	@ResponseBody
	public Map<String,Object> getLastSalaryTotal(String year_,String month_){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return teacherSalaryService.getLastSalaryTotal(username,year_,month_);
	}
	
	@RequestMapping("/lastSalarySubtract")
	@ResponseBody
	public List<Map<String,Object>> getLastSalarySubtract(String year_,String month_){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return teacherSalaryService.getLastSalarySubtract(username,year_,month_);
	}
	
	@RequestMapping("/lastSalarySubtractTotal")
	@ResponseBody
	public Map<String,Object> getLastSalarySubtractTotal(String year_,String month_){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return teacherSalaryService.getLastSalarySubtractTotal(username,year_,month_);
	}
}
