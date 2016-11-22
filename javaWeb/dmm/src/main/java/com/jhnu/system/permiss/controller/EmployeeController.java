package com.jhnu.system.permiss.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.system.permiss.service.UserService;


@Controller
public class EmployeeController {

	@Autowired
	private UserService userService;
	

	private static final String XML_VIEW_NAME = "employees";
	
	@RequiresRoles("user")
	@RequestMapping(method=RequestMethod.GET, value="/employee/{id}")
	public ModelAndView getEmployee(@PathVariable String id) {
		ModelAndView mv=new ModelAndView(XML_VIEW_NAME);
		mv.addObject("mothodName", "GET---/employee/{id}");
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/employee/{id}")
	public ModelAndView updateEmployee(@PathVariable String id,@RequestBody String body) {
		ModelAndView mv=new ModelAndView(XML_VIEW_NAME);
		mv.addObject("mothodName", "PUT---/employee/{id}");
		Subject subject = SecurityUtils.getSubject();  
		if(subject.isPermitted("menu:create")) {  
			mv.addObject("rolePermi", "有menu:create权限");
		}else{
			mv.addObject("rolePermi", "无menu:create权限");
		}
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/employee")
	public ModelAndView addEmployee() {
		ModelAndView mv=new ModelAndView(XML_VIEW_NAME);
		mv.addObject("mothodName", "POST---/employee");
		Subject subject = SecurityUtils.getSubject();  
		if(subject.hasRole("user")) {  
			mv.addObject("rolePermi", "有user权限");
		}else{
			mv.addObject("rolePermi", "无user权限");
		}
		return mv;
	}
	
	
	@RequestMapping(method=RequestMethod.DELETE, value="/employee/{id}")
	@RequiresRoles("admin")
	public ModelAndView removeEmployee(@PathVariable String id) {
		ModelAndView mv=new ModelAndView(XML_VIEW_NAME);
		mv.addObject("mothodName", "DELETE---/employee/{id}");
		Subject subject = SecurityUtils.getSubject();  
		if(subject.hasRole("admin")) {  
			mv.addObject("rolePermi", "有admin权限");
		}else{
			mv.addObject("rolePermi", "无admin权限");
		}
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/employees")
	public ModelAndView getEmployees() {
		//测试Exception
		
		ModelAndView mv=new ModelAndView(XML_VIEW_NAME);
		mv.addObject("mothodName", "GET---/employees");
		Subject subject = SecurityUtils.getSubject();  
		if(subject.isPermittedAll("user:create","menu:view")) {  
			mv.addObject("rolePermi", "有user:create,menu:view权限");
		}else{
			mv.addObject("rolePermi", "无user:create,menu:view权限");
		}
		return mv;
		
	}

	
	
}
