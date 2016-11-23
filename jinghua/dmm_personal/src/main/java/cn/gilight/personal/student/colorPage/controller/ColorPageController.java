package cn.gilight.personal.student.colorPage.controller;
import java.util.Map;
import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhnu.syspermiss.util.ContextHolderUtils;

import cn.gilight.personal.student.colorPage.service.ColorPageService;

@Controller("colorPageController")
@RequestMapping("/student/colorPage")
public class ColorPageController {
	private Logger log = Logger.getLogger(ColorPageController.class);
	@Autowired
	private ColorPageService colorPageService;
	
	@RequestMapping("/school")
	@ResponseBody
	public Map<String,Object> getSchool(){
		return colorPageService.getSchool();
	}
	
	@RequestMapping("/fdy")
	@ResponseBody
	public Map<String,Object> getFdy(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		return colorPageService.getFdy(username);
	}
	
	@RequestMapping("/kc")
	@ResponseBody
	public Map<String,Object> getKc(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		return colorPageService.queryCourse(username);
	}
	
	@RequestMapping("/stu")
	@ResponseBody
	public Map<String,Object> getStu(){
		return colorPageService.queryStuSex();
	}
	
	@RequestMapping("/tmx")
	@ResponseBody
	public Map<String,Object> getTmx(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		return colorPageService.queryTmx(username);
	}
	
	@RequestMapping("/tx")
	@ResponseBody
	public Map<String,Object> getTx(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		return colorPageService.queryTx(username);
	}
	
	@RequestMapping("/yc")
	@ResponseBody
	public Map<String,Object> getYc(){
		return colorPageService.QueryRestaurantWinTop();
	}
	
	@RequestMapping("/major")
	@ResponseBody
	public Map<String,Object> getMajor(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		return colorPageService.QueryMajorSex(username);
	}

}