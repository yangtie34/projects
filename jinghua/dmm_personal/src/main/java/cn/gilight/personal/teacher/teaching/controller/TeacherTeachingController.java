package cn.gilight.personal.teacher.teaching.controller;

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
import cn.gilight.personal.teacher.teaching.service.TeacherTeachingService;

@Controller("teacherTeachingController")
@RequestMapping("/teacher/teaching")
public class TeacherTeachingController {
	private Logger log = Logger.getLogger(TeacherMainController.class);
	@Autowired
	private TeacherTeachingService teacherTeachingService;
	
	@RequestMapping("/todayClass")
	@ResponseBody
	public List<Map<String,Object>> getTodayClass(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return teacherTeachingService.getTodayClass(username);
	}
	
	@RequestMapping("/termClass")
	@ResponseBody
	public List<Map<String,Object>> getTermClass(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return teacherTeachingService.getTermClass(username);
	}
	
	
	
	@RequestMapping("/todayCourse")
	@ResponseBody
	public List<Map<String,Object>> getTodayCourse(String courseArrangementId,String courseId){
		return teacherTeachingService.getTodayCourse(courseArrangementId,courseId);
	}
	
	@RequestMapping("/classSchedule")
	@ResponseBody
	public List<Map<String,Object>> getClassSchedule(String week){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return teacherTeachingService.getClassSchedule(username,Integer.parseInt(week));
	}
	
	@RequestMapping("/week")
	@ResponseBody
	public Map<String,Object> getWeek(String week,String zyrq,String flag){
		return teacherTeachingService.getWeek(Integer.parseInt(week),zyrq,flag);
	}
	
	
	
}
