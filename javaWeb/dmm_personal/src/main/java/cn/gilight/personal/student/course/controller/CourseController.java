package cn.gilight.personal.student.course.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhnu.syspermiss.util.ContextHolderUtils;
import cn.gilight.personal.student.course.service.CourseService;
import cn.gilight.personal.teacher.main.controller.TeacherMainController;

@Controller("courseController")
@RequestMapping("/student/course")
public class CourseController {
	private Logger log = Logger.getLogger(TeacherMainController.class);
	@Autowired
	private CourseService courseService;
	
	@RequestMapping("/today")
	@ResponseBody
	public Map<String,Object> getToday(){
		return courseService.getToday();
	}
	
	@RequestMapping("/todayCourse")
	@ResponseBody
	public List<Map<String,Object>> getTodayCourse(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return courseService.getTodayCourse(username);
	}
	
	@RequestMapping("/schedule")
	@ResponseBody
	public List<Map<String,Object>> getSchedule(String week){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return courseService.getSchedule(username,Integer.parseInt(week));
	}
	
	@RequestMapping("/week")
	@ResponseBody
	public Map<String,Object> getWeek(String week,String zyrq,String flag){
		return courseService.getWeek(Integer.parseInt(week),zyrq,flag);
	}
	
	
	
}
