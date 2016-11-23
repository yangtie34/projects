package cn.gilight.product.dorm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.gilight.framework.code.Code;

@Controller
@RequestMapping("dorm")
public class DormController {
	@RequestMapping(value="/dormRke",method=RequestMethod.GET)
	public ModelAndView dormRke(){
		ModelAndView mv=new ModelAndView("/dorm/dormRke");
		mv.addObject("dormUesd", Code.getKey("dorm.uesd"));
		return mv;
	}
	@RequestMapping(value="/dormEmploy",method=RequestMethod.GET)
	public ModelAndView dormEmploy(){
		ModelAndView mv=new ModelAndView("/dorm/dormEmploy");
		return mv;
	}
	@RequestMapping(value="/dormStu",method=RequestMethod.GET)
	public ModelAndView dormStu(){
		ModelAndView mv=new ModelAndView("/dorm/dormStu");
		return mv;
	}
}
