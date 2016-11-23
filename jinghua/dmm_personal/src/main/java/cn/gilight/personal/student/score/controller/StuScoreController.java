package cn.gilight.personal.student.score.controller;


import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhnu.syspermiss.util.ContextHolderUtils;
import cn.gilight.personal.student.score.service.StuScoreService;
import cn.gilight.personal.teacher.main.controller.TeacherMainController;

@Controller("stuScoreController")
@RequestMapping("/student/score")
public class StuScoreController {
	private Logger log = Logger.getLogger(TeacherMainController.class);
	
	@Autowired
	private StuScoreService stuScoreService;
	
	@RequestMapping("/lastScore")
	@ResponseBody
	public Map<String,Object> getLastScore(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return stuScoreService.getLastScore(username);
	}
	
	@RequestMapping("/proportion")
	@ResponseBody
	public Map<String,Object> getProportion(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return stuScoreService.getProportion(username);
	}
	
	@RequestMapping("/scoreList")
	@ResponseBody
	public List<Map<String,Object>> getScoreList(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return stuScoreService.getScoreList(username);
	}
	
	@RequestMapping("/credit")
	@ResponseBody
	public Map<String,Object> getCredit(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return stuScoreService.getCredit(username);
	}
	
	@RequestMapping("/creditType")
	@ResponseBody
	public Map<String,Object> getCreditType(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return stuScoreService.getCreditType(username);
	}
	
	
	
}
