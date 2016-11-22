package cn.gilight.personal.student.card.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhnu.syspermiss.util.ContextHolderUtils;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.personal.student.card.service.CardService;

/**   
* @Description: 一卡通消费情况
* @author Sunwg  
* @date 2016年4月27日 下午2:30:53   
*/
@Controller("studentCardController")
@RequestMapping("/student/card")
public class CardController {
	private Logger log = Logger.getLogger(CardController.class);
	@Resource
	private CardService cardService;
	
	/** 
	* @Description: 查询一卡通余额
	* @return Map<String,Object>
	*/
	@RequestMapping("/queryYktYe")
	@ResponseBody
	public Map<String, Object> queryYktYe() {
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		Map<String, Object> result = new HashMap<String, Object>();
		log.debug(username+ "查询一卡通余额");
		List<Map<String, Object>> xflist = cardService.queryConsumeOfDay(username, DateUtils.getYesterday());
		result.put("ye", cardService.queryCardBalance(username));
		result.put("xflist", xflist);
		return result;
	}
	
	/** 
	* @Description: 查询一卡通月消费明细
	* @return Map<String,Object>
	*/
	@RequestMapping("/queryYktMonthConsume")
	@ResponseBody
	public Page queryYktMonthConsume(Page page) {
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username+ "查询一卡通余额");
		page = cardService.queryConsumeOfMonth(username, page);
		return page;
	}
	
	/** 
	* @Description: 查询一卡通月消费明细
	* @return Map<String,Object>
	*/
	@RequestMapping("/queryYktMonthConsumeDetail")
	@ResponseBody
	public Page queryYktMonthConsumeDetail(Page page,String month) {
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		page = cardService.queryConsumeDetailOfMonth(username, page, month);
		return page;
	}
	
	/** 
	* @Description: 查询学生的总消费信息
	* @return Map<String,Object>
	*/
	@RequestMapping("/queryStudentConsumeTotal")
	@ResponseBody
	public Map<String, Object> queryStudentConsumeTotal(){
		//获取用户名
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		
		Map<String, Object> result = new HashMap<String, Object>();
		//计算和学生同级的学生人数
		int totalStus = cardService.querySameGradeNumsOFStudent(username);
		//查询学生的总消费金额
		float totalConsume = cardService.queryTotalConsumeOfStudent(username);
		//查询消费总额高于当前学生的人数
		int higherNums = cardService.queryConsumeTotalHigherThenStudentNums(username);
		//计算当前学生消费超越的学生百分比
		float higher = new Float((totalStus - higherNums-1)*100)/totalStus;
		String higherPersent = (float)(Math.round(100*higher))/100 +"%";
		//查询当前学生消费总额的消费类别占比
		List<Map<String, Object>> list = cardService.queryDealsOfStudentTotalConsume(username);
		
		result.put("total", totalConsume);
		result.put("higherPersent",higherPersent);
		result.put("dealList", list);
		return result;
	}
}