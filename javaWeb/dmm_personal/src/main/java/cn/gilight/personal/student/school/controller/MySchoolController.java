package cn.gilight.personal.student.school.controller;

import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gilight.personal.student.school.service.MySchoolService;
import cn.gilight.personal.teacher.main.controller.TeacherMainController;

@Controller("mySchoolController")
@RequestMapping("/student/school")
public class MySchoolController {
	private Logger log = Logger.getLogger(TeacherMainController.class);
	@Autowired
	private MySchoolService mySchoolService;
	
	@RequestMapping("/school")
	@ResponseBody
	public Map<String,Object> getSchool(){
		return mySchoolService.getSchool();
	}
	
	@RequestMapping("/counts")
	@ResponseBody
	public Map<String,Object> getCounts(){
		return mySchoolService.getCounts();
	}
	
	@RequestMapping("/peopleCounts")
	@ResponseBody
	public Map<String,Object> getPeopleCounts(){
		return mySchoolService.getPeopleCounts();
	}
	
	
}
