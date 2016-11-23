package com.jhnu.system.permiss.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.system.permiss.service.ResourcesService;
import com.jhnu.system.permiss.service.RoleService;



@Controller
@RequestMapping("/system/role")
public class RoleController {
	@Autowired
	private RoleService  roleService;
	@Autowired
	private ResourcesService resourcesService; 
	
	@RequestMapping(method=RequestMethod.GET, value="/getrole")
	public ModelAndView getRole(){
		ModelAndView mv=new ModelAndView("/system/permiss/role");
		
		List<Map<String,Object>> roleTypes = roleService.findRoleType();
		String nodes = resourcesService.getAllResNodeJson();
		mv.addObject("resJson", nodes);
		mv.addObject("roleTypes", roleTypes);
		return mv;
	}
	
	
	
	
	
	
}
