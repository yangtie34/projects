package com.jhnu.product.manager.dept.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.product.common.school.service.DeptTreeService;

@Controller
@RequestMapping("/manager/depttree")
public class DeptTreeController {
	
	@Autowired
	private DeptTreeService deptTreeService;
	
	@RequestMapping(method=RequestMethod.GET,value="/deptTeacherTree")
	public ModelAndView deptTeacherTree(){
		ModelAndView mv = new ModelAndView("/manager/depttree/deptTeacherTree") ;
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/deptPermsTree")
	public ModelAndView deptTree(){
		ModelAndView mv = new ModelAndView("/manager/depttree/deptPermsTree") ;
		return mv;
	}
}
