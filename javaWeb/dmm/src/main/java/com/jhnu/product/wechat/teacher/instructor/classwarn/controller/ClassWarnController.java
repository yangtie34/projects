package com.jhnu.product.wechat.teacher.instructor.classwarn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.product.common.stu.entity.LateDormStudent;
import com.jhnu.product.common.stu.service.StuService;
import com.jhnu.product.common.teacher.service.TeacherService;
import com.jhnu.product.wechat.teacher.instructor.classwarn.service.ClassWarnService;
import com.jhnu.util.common.DateUtils;

@Controller
@RequestMapping("/wechat/teacher/instructor")
public class ClassWarnController {
	
	@Autowired
	private ClassWarnService classWarnService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private StuService stuService;
	
	@RequestMapping(method=RequestMethod.GET,value="/{is_wechat}/classwarn/latedorm/{code}")
	public ModelAndView getClassWarn(@PathVariable("is_wechat") String is_wechat,@PathVariable("code") String code){
		ModelAndView mv=new ModelAndView("/wechat/teacher/instructor/warn");
		mv.addObject("classWarn",classWarnService.getClassLateDormByTeacherId(code, DateUtils.getYesterday()));
		mv.addObject("tea",teacherService.getTeacherInfo(code) );
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{is_wechat}/classwarn/latedorm/stu/{classid}")
	public ModelAndView getClassWarnStu(@PathVariable("is_wechat") String is_wechat,@PathVariable("classid") String classid){
		ModelAndView mv=new ModelAndView("/wechat/teacher/instructor/warnStu");
		LateDormStudent lds=new LateDormStudent();
		lds.setAction_date(DateUtils.getYesterday());
		lds.setClass_id(classid);
		mv.addObject("tea",teacherService.getInstructorInfoByThisYearTerm(classid));
		mv.addObject("lateStu", stuService.getLateDormStudents(lds));
		//TODO 此处用的是班级ID，需要通过班级ID，获取班级信息，传班级信息。
		mv.addObject("class_name", classid);
		return mv;
	}
	
}
