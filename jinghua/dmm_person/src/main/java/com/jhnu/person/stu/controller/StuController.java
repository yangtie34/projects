package com.jhnu.person.stu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/stu")
public class StuController {	
	private static ModelAndView mv=new ModelAndView();
	private static String rootRole="stu";
/*	static{
		mv.addObject("rootRole", rootRole);
	}*/
	@RequestMapping(method=RequestMethod.GET,value="/StuInfo")
	public ModelAndView stuInfo(){
		mv.setViewName("person/stu/StuInfo");
		return mv;
	}
	@RequestMapping(value="/StuQj",method=RequestMethod.GET)
	public ModelAndView getStuQj(){
		mv.setViewName("person/stu/StuQj");
		return mv;
	}
	@RequestMapping(method=RequestMethod.GET,value="/StuSchLife")
	public ModelAndView StuSchLife(){
		mv.setViewName("person/stu/StuSchLife");
		return mv;
	}
	@RequestMapping(method=RequestMethod.GET,value="/StuStudy")
	public ModelAndView StuStudy(){
		mv.setViewName("person/stu/StuStudy");
		return mv;
	}
	@RequestMapping(method=RequestMethod.GET, value="/{id}")///roleid/{id}")
	public ModelAndView stuInfo1(@PathVariable("id") String id){
		//教师
		mv.setViewName("person/stu/StuInfoAll");//"+id);
		mv.addObject("id", id);
		return mv;
	}
	
	
	
}
