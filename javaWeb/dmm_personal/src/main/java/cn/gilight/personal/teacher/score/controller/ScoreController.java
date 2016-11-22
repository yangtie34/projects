package cn.gilight.personal.teacher.score.controller;

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
import cn.gilight.personal.teacher.score.service.ScoreService;

@Controller("scoreController")
@RequestMapping("/teacher/score")
public class ScoreController {
	private Logger log = Logger.getLogger(TeacherMainController.class);
	@Autowired
	private ScoreService scoreService;
	
	@RequestMapping("/scoreClasses")
	@ResponseBody
	public List<Map<String,Object>> getScoreClasses(String school_year,String term_code){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return scoreService.getScoreClasses(school_year,term_code,username);
	}
	
	@RequestMapping("/courseScore")
	@ResponseBody
	public List<Map<String,Object>> getCourseScore(String school_year,String term_code){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return scoreService.getCourseScore(school_year, term_code, username);
	}
	
	@RequestMapping("/stuScore")
	@ResponseBody
	public List<Map<String,Object>> getStuScore(String class_id,String course_id,String param,String school_year,String term_code,String paramFlag){
		return scoreService.getStuScore(school_year,term_code,class_id,course_id,param,paramFlag);
	}
	
	@RequestMapping("/stuTotalScore")
	@ResponseBody
	public List<Map<String,Object>> getStuTotalScore(String stu_id,String school_year,String term_code){
		return scoreService.getStuTotalScore(school_year,term_code,stu_id);
	}
	
	@RequestMapping("/stuScoreDetail")
	@ResponseBody
	public List<Map<String,Object>> getStuScoreDetail(String stu_id,String school_year,String term_code){
		return scoreService.getStuScoreDetail(school_year,term_code,stu_id);
	}
	
	
}
