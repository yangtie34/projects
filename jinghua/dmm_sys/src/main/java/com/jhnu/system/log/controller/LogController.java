package com.jhnu.system.log.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/log")
public class LogController {
	
	@RequestMapping(method=RequestMethod.GET, value="/changePwdLog")
	public ModelAndView changePwd(){
		ModelAndView mv=new ModelAndView("/log/changePwdLog");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="/loginLog",method=RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView mv=new ModelAndView("/log/loginLog");
		return mv;
	}
}
