package com.jhnu.product.wechat.teacher.instructor.classbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.product.common.teacher.service.TeacherService;
import com.jhnu.product.wechat.teacher.instructor.classbase.service.ClassBaseService;

@Controller
@RequestMapping("/wechat/teacher/instructor")
public class ClassBaseController {
	
	@Autowired
	private ClassBaseService classBaseService;
	@Autowired
	private TeacherService teacherService;
	
	@RequestMapping(method=RequestMethod.GET,value="/{is_wechat}/classbase/{code}")
	public ModelAndView getClassBase(@PathVariable("is_wechat") String is_wechat,@PathVariable("code") String code){
		ModelAndView mv=new ModelAndView("/wechat/teacher/instructor/classBase");
		mv.addObject("classes",classBaseService.getClassBaseByTeacherId(code) );
		mv.addObject("tea",teacherService.getTeacherInfo(code) );
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{is_wechat}/classbase/{classid}/{stutype}")
	public ModelAndView getClassStu(@PathVariable("is_wechat") String is_wechat,@PathVariable("stutype") String stutype,@PathVariable("classid") String classid){
		ModelAndView mv=new ModelAndView("/wechat/teacher/instructor/classStu");
		boolean isDuties=true;
		if("all".equals(stutype)){
			isDuties=false;
		}
		mv.addObject("stus",classBaseService.getClassStuByClassId(classid,isDuties));
		mv.addObject("tea",teacherService.getInstructorInfoByThisYearTerm(classid));
		//TODO 此处用的是班级ID，需要通过班级ID，获取班级信息，传班级信息。
		mv.addObject("class_name", classid);
		
		return mv;
	}
	
}
