package cn.gilight.personal.teacher.dailylife.controller;


import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhnu.syspermiss.util.ContextHolderUtils;
import cn.gilight.framework.page.Page;
import cn.gilight.personal.teacher.dailylife.service.DailyLifeService;
import cn.gilight.personal.teacher.main.controller.TeacherMainController;

@Controller("dailyLifeController")
@RequestMapping("/teacher/dailylife")
public class DailyLifeController {
	private Logger log = Logger.getLogger(TeacherMainController.class);
	@Autowired
	private DailyLifeService dailyLifeService;
	
	
	@RequestMapping("/monthConsume")
	@ResponseBody
	public Map<String,Object> getMonthConsume(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return dailyLifeService.getMonthConsume(username);
	}
	
	@RequestMapping("/totalConsume")
	@ResponseBody
	public Map<String,Object> getTotalConsume(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return dailyLifeService.getTotalConsume(username);
	}
	
	@RequestMapping("/borrow")
	@ResponseBody
	public Map<String,Object> getBorrow(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return dailyLifeService.getBorrow(username);
	}
	
	@RequestMapping("/inBorrow")
	@ResponseBody
	public List<Map<String,Object>> getInBorrow(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return dailyLifeService.getInBorrow(username);
	}
	@RequestMapping("/outOfDateBorrow")
	@ResponseBody
	public List<Map<String,Object>> getOutOfDateBorrow(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return dailyLifeService.getOutOfDateBorrow(username);
	}
	@RequestMapping("/returnBorrow")
	@ResponseBody
	public List<Map<String,Object>> getReturnBorrow(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return dailyLifeService.getReturnBorrow(username);
	}
	
	
	
	@RequestMapping("/recommentBorrow")
	@ResponseBody
	public List<Map<String,Object>> getRecommendedBorrow(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return dailyLifeService.getRecommendedBook(username);
	}
	
	@RequestMapping("/payHistory")
	@ResponseBody
	public List<Map<String,Object>> getPayHistory(String currYear){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return dailyLifeService.getPayHistory(username,currYear);
	}
	
	@RequestMapping("/monthConsumeListPage")
	@ResponseBody
	public Page getMonthConsumeListPage(String monthStart,String currpage,String flag){
		Page page = new Page();
		page.setCurpage(Integer.parseInt(currpage));
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return dailyLifeService.getMonthConsumeList(username,monthStart,page,flag);
	}
	
	@RequestMapping("/monthPayType")
	@ResponseBody
	public Map<String,Object> getMonthPayType(String monthStart){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return dailyLifeService.getMonthPayType(username,monthStart);
	}
	
	@RequestMapping("/stopCounts")
	@ResponseBody
	public Map<String,Object> getStopCounts(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return dailyLifeService.getStopCounts(username);
	}
	
	@RequestMapping("/stopTimeAvg")
	@ResponseBody
	public Map<String,Object> getStopTimeAvg(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return dailyLifeService.getStopTimeAvg(username);
	}
	
	@RequestMapping("/carStop")
	@ResponseBody
	public List<Map<String,Object>> getCarStop(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return dailyLifeService.getCarStop(username);
	}
}
