package com.jhnu.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.syspermiss.util.UserUtil;




@Controller
@RequestMapping("/edu")
public class EduController {	
	static ModelAndView mv=new ModelAndView();
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView index(){
		//String userName = UserUtil.getCasLoginName();
		//TODO edu根据权限转发不同地址
		//List<Resources> menus=GetCachePermiss.getSysMenusByUserName(userName);
		//mv.setViewName("redirect:/common/errorUser"); //用户无此功能 返回登录
		//mv.setViewName("redirect:/common/nopermiss"); //用户无此页面权限
		mv.setViewName("redirect:/edu/SchoolList");
		return mv;
	}
	
	@RequestMapping(value="/SchoolList",method=RequestMethod.GET)
	public ModelAndView getSchoolList(){
		mv.setViewName("edu/SchoolList");
		return mv;
	}
/*	@RequestMapping(value="/SchoolList",method=RequestMethod.GET)
	public ModelAndView getSchoolList(){
		mv.setViewName("edu/SchoolList");
		return mv;
	}
	@RequestMapping(value="/SchoolList",method=RequestMethod.GET)
	public ModelAndView getSchoolList(){
		mv.setViewName("edu/SchoolList");
		return mv;
	}*/
	@RequestMapping(method=RequestMethod.GET, value="SchoolQJ/{schoolId}")
	public ModelAndView getSchoolQJ(@PathVariable("schoolId") String schoolId){
		mv.setViewName("edu/SchoolQJ");//"+id);
		mv.addObject("schoolId", schoolId);
		return mv;
	}
}
