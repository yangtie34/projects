package com.jhnu.product.manager.teacher.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.product.common.school.service.DeptPermissionService;
import com.jhnu.product.manager.school.service.DeptService;
import com.jhnu.product.manager.teacher.service.ITeacherEmpInfoService;

@Controller
@RequestMapping("/manager/teacher")
public class TeaInfoController {

	@Autowired
	private ITeacherEmpInfoService teacherEmpInfoService;
	@Autowired
	private DeptPermissionService dataPermissionService;
	@Autowired
	private DeptService deptService;

	@RequestMapping(method = RequestMethod.GET, value = "/tea")
	public ModelAndView getTeaDept() {
		ModelAndView mv = new ModelAndView("/manager/teacher/teainfo");
		// HttpSession session = ContextHolderUtils.getSession();
		// User user=(User)session.getAttribute(Globals.USER_SESSION);
		// 获取数据权限
		// String ids = dataPermissionService.getDeptTeachPermsStrByUserId(user.getId());
		String ids = "0";
		List<Map<String, Object>> depts = deptService.getDept(ids);
		//teacherEmpInfoService
		mv.addObject("depts", depts);
		mv.addObject("dept_id", ids);
		return mv;
	}
/*
	@RequestMapping(method = RequestMethod.GET, value = "/tea/{dept_id}")
	public ModelAndView getTeaDeptLeaf(@PathVariable("dept_id") String dept_id) {
		ModelAndView mv = new ModelAndView("/manager/teacher/teainfo");
		String ids = dept_id;
		List<Map<String, Object>> depts = deptService.getDeptLeaf(dept_id);
		mv.addObject("depts", depts);
		mv.addObject("ids", ids);
		return mv;
	}*/
	
	@RequestMapping(method=RequestMethod.GET,value="/teanum")
	public ModelAndView getTeaNum(){
		ModelAndView mv = new ModelAndView("/manager/teacher/teanum") ;
		//HttpSession session = ContextHolderUtils.getSession();
		//User user=(User)session.getAttribute(Globals.USER_SESSION);
		//获取数据权限
		//String ids = dataPermissionService.getDeptTeachPermsStrByUserId(user.getId());
		String ids = "0";
		mv.addObject("ids", ids);
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/tea/teachingTask")
	public ModelAndView getTeachingTask() {
		ModelAndView mv = new ModelAndView("/manager/teacher/teachingTask");
		String ids = "0";
		mv.addObject("deptId", ids);
		return mv;
	}

}
