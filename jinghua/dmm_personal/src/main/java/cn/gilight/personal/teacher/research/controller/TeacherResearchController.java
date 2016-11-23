package cn.gilight.personal.teacher.research.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhnu.syspermiss.util.ContextHolderUtils;
import cn.gilight.personal.teacher.main.controller.TeacherMainController;
import cn.gilight.personal.teacher.research.service.TeacherResearchService;

@Controller("teacherResearchController")
@RequestMapping("/teacher/research")
public class TeacherResearchController {
	private Logger log = Logger.getLogger(TeacherMainController.class);
	
	@Resource
	private TeacherResearchService teacherResearchService;
	
	@RequestMapping("/researchCounts")
	@ResponseBody
	public Map<String,Object> getResearchCounts(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return teacherResearchService.getResearchCounts(username);
	}
	
	@RequestMapping("/thesisCounts")
	@ResponseBody
	public Map<String,Object> getThesisCounts(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return teacherResearchService.getThesisCounts(username);
	}
	
	@RequestMapping("/thesises")
	@ResponseBody
	public List<Map<String,Object>> getThesises(String flag){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return teacherResearchService.getThesis(username,flag);
	}
	
	@RequestMapping("/projectCounts")
	@ResponseBody
	public Map<String,Object> getProjectCounts(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return teacherResearchService.getProjectCounts(username);
	}
	

	@RequestMapping("/projects")
	@ResponseBody
	public List<Map<String,Object>> getProjects(String flag){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return teacherResearchService.getProjects(username,flag);
	}
	
	@RequestMapping("/works")
	@ResponseBody
	public List<Map<String,Object>> getWorks(String flag){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username =  principal.getName();
		log.debug(username);
		return teacherResearchService.getWorks(username,flag);
	}
	
	@RequestMapping("/worksCounts")
	@ResponseBody
	public Map<String,Object> getWorksCounts(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username =  principal.getName();
		log.debug(username);
		return teacherResearchService.getWorksCounts(username);
	}
	
	@RequestMapping("/patentCounts")
	@ResponseBody
	public Map<String,Object> getPatentCounts(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username =  principal.getName();
		log.debug(username);
		return teacherResearchService.getPatentCounts(username);
	}
	

	@RequestMapping("/patents")
	@ResponseBody
	public List<Map<String,Object>> getPatents(String flag){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username =  principal.getName();
		log.debug(username);
		return teacherResearchService.getPatents(username,flag);
	}
	

	@RequestMapping("/outcomeCounts")
	@ResponseBody
	public Map<String,Object> getOutcomeCounts(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username =  principal.getName();
		log.debug(username);
		return teacherResearchService.getOutcomeCounts(username);
	}
	

	@RequestMapping("/outcomes")
	@ResponseBody
	public List<Map<String,Object>> getOutcomes(String flag){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return teacherResearchService.getOutcomes(username,flag);
	}
	
	@RequestMapping("/softs")
	@ResponseBody
	public List<Map<String,Object>> getSofts(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username =  principal.getName();
		log.debug(username);
		return teacherResearchService.getSofts(username);
	}
	
	
	
	
	
}
