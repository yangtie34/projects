package cn.gilight.personal.student.major.controller;

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
import cn.gilight.personal.student.major.service.MyMajorService;
import cn.gilight.personal.teacher.main.controller.TeacherMainController;

@Controller("myMajorController")
@RequestMapping("/student/major")
public class MyMajorController {
	private Logger log = Logger.getLogger(TeacherMainController.class);
	@Autowired
	private MyMajorService myMajorService;
	
	@RequestMapping("/major")
	@ResponseBody
	public Map<String,Object> getMajor(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return myMajorService.getMajor(username);
	}
	
	@RequestMapping("/course")
	@ResponseBody
	public List<Map<String,Object>> getCourse(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return myMajorService.getCourse(username);
	}
	
	@RequestMapping("/courseScore")
	@ResponseBody
	public List<Map<String,Object>> getCourseScore(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return myMajorService.getCourseScore(username);
	}
	
	@RequestMapping("/chooseCourse")
	@ResponseBody
	public Page getChooseCourse(String currpage){
		return myMajorService.getChooseCourse(Integer.parseInt(currpage));
	}
	
	@RequestMapping("/postgraduate")
	@ResponseBody
	public Page getPostgraduate(String currpage){
		return myMajorService.getPostgraduate(Integer.parseInt(currpage));
	}
	
}
