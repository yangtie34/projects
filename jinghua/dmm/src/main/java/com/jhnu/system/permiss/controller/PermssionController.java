package com.jhnu.system.permiss.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.jhnu.product.common.school.service.DeptTreeService;
import com.jhnu.system.permiss.entity.Operate;
import com.jhnu.system.permiss.entity.Role;
import com.jhnu.system.permiss.entity.User;
import com.jhnu.system.permiss.service.DataServeService;
import com.jhnu.system.permiss.service.OperateService;
import com.jhnu.system.permiss.service.PermssionService;
import com.jhnu.system.permiss.service.ResourcesService;
import com.jhnu.system.permiss.service.RoleService;
import com.jhnu.system.permiss.service.UserService;



@Controller
@RequestMapping("/system/permssion")
public class PermssionController {
	@Autowired
	private PermssionService  permssionService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private DataServeService dataServeService;
	@Autowired
	private OperateService operateService;
	@Autowired
	private ResourcesService resourcesService; 
	@Autowired
	private DeptTreeService deptTreeService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(method=RequestMethod.GET, value="/roleid/{id}")
	public ModelAndView getPermssionRole(@PathVariable("id") Long roleId){
		ModelAndView mv=new ModelAndView("/system/permiss/permssion");
		
		Role role = roleService.findByRoleId(roleId);
		if(role != null){
			List<Operate> operateList = operateService.findAll();
			List<Map<String,Object>> dataSeverList = dataServeService.findAll();
			Object nodes = JSON.toJSON(resourcesService.getResourceAngularTree(null));
			String zzjgJson=deptTreeService.getDeptJson();
			String jxjgJson=deptTreeService.getDeptTeachJson();
			mv.addObject("resJson", nodes);
			mv.addObject("zzjgJson", zzjgJson);
			mv.addObject("jxjgJson", jxjgJson);
			mv.addObject("operateList", operateList);
			mv.addObject("dataSeverList", dataSeverList);
		}
		mv.addObject("flag", "role");
		mv.addObject("role", role);
		
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/userid/{id}")
	public ModelAndView getPermssionUser(@PathVariable("id") Long userId){
		ModelAndView mv=new ModelAndView("/system/permiss/permssion");
		
		User u = new User();
		u.setId(userId);
		List<User> users = userService.getAllUsers(u);
		if(users != null && users.size()>0){
			u = users.get(0);
		}
		if(u != null){
			List<Operate> operateList = operateService.findAll();
			List<Map<String,Object>> dataSeverList = dataServeService.findAll();
			Object nodes = JSON.toJSON(resourcesService.getResourceAngularTree(null));
			String zzjgJson=deptTreeService.getDeptJson();
			String jxjgJson=deptTreeService.getDeptTeachJson();
			mv.addObject("resJson", nodes);
			mv.addObject("zzjgJson", zzjgJson);
			mv.addObject("jxjgJson", jxjgJson);
			mv.addObject("operateList", operateList);
			mv.addObject("dataSeverList", dataSeverList);
		}
		mv.addObject("user", u);
		mv.addObject("flag", "user");
		return mv;
	}
	
	
	
	
	
	
}
