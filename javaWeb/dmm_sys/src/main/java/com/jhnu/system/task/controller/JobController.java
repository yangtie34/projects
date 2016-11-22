package com.jhnu.system.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.jhnu.system.task.entity.Plan;
import com.jhnu.system.task.entity.Work;
import com.jhnu.system.task.service.PlanService;
import com.jhnu.system.task.service.WorkService;
import com.jhnu.util.common.BeanMapUtil;

@Controller
@RequestMapping("/system/task")
public class JobController {
	@Autowired
	private PlanService PlanService;
	@Autowired
	private WorkService WorkService;
	
	@RequestMapping(method=RequestMethod.GET, value="/plan")
	public ModelAndView getPlanPage() {
		ModelAndView mv=new ModelAndView("/system/task/plan");
		return mv;
	}
	@RequestMapping(method=RequestMethod.GET, value="/work")
	public ModelAndView getWorkPage() {
		ModelAndView mv=new ModelAndView("/system/task/work");
		return mv;
	}
	@RequestMapping(method=RequestMethod.GET, value="/verify")
	public ModelAndView getVerifyPage() {
		ModelAndView mv=new ModelAndView("/system/task/verify");
		return mv;
	}
	@RequestMapping(method=RequestMethod.GET, value="/sysGroup")
	public ModelAndView getSysGroupPage() {
		ModelAndView mv=new ModelAndView("/system/task/sysGroup");
		return mv;
	}
	@RequestMapping(method=RequestMethod.GET, value="/workVerify/{id}")
	public ModelAndView getWorkVerifyPage(@PathVariable("id") String id) {
		ModelAndView mv=new ModelAndView("/system/task/workVerify");
		Work work=WorkService.getWorkById(id);
		mv.addObject("work", JSON.toJSONString(work));
		return mv;
	}
	@RequestMapping(method=RequestMethod.GET, value="/planWork/{id}")
	public ModelAndView getPlanWorkPage(@PathVariable("id") String id) {
		ModelAndView mv=new ModelAndView("/system/task/planWork");
		Plan plan = PlanService.getPlanById(id);
		mv.addObject("plan", JSON.toJSONString(plan));
		return mv;
	}
	@RequestMapping(method=RequestMethod.GET, value="/planLog/{id}")
	public ModelAndView getPlanLogPage(@PathVariable("id") String id) {
		ModelAndView mv=new ModelAndView("/system/task/planLog");
		Plan plan = PlanService.getPlanById(id);
		mv.addObject("plan", JSON.toJSONString(plan));
		return mv;
	}
}
