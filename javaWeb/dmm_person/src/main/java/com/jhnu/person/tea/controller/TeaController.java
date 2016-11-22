package com.jhnu.person.tea.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;




@Controller
@RequestMapping("/tea")
public class TeaController {	
	static ModelAndView mv=new ModelAndView("person/tea/index");
/*	private static String rootRole="tea";
		static{
			mv.addObject("rootRole", rootRole);
		}*/
	
	@RequestMapping(value="/TeaInfo",method=RequestMethod.GET)
	public ModelAndView getTeaInfo(){
		mv.setViewName("person/tea/TeaInfo");
		return mv;
	}
	@RequestMapping(value="/TeaQj",method=RequestMethod.GET)
	public ModelAndView getTeaQj(){
		mv.setViewName("person/tea/TeaQj");
		return mv;
	}
	@RequestMapping(value="/TeaInfoAll",method=RequestMethod.GET)
	public ModelAndView getTeaInfoAll(){
		mv.setViewName("person/tea/TeaInfoAll");
		return mv;
	}
	@RequestMapping(value="/TeaKy",method=RequestMethod.GET)
	public ModelAndView getTeaKy(){
		mv.setViewName("person/tea/TeaKy");
		return mv;
	}
	@RequestMapping(value="/TeaTeach",method=RequestMethod.GET)
	public ModelAndView getTeaTeach(){
		mv.setViewName("person/tea/TeaTeach");
		return mv;
	}
	@RequestMapping(value="/TeaSchLife",method=RequestMethod.GET)
	public ModelAndView getTeaSchLife(){
		mv.setViewName("person/tea/TeaSchLife");
		return mv;
	}
	@RequestMapping(value="/TeaStu",method=RequestMethod.GET)
	public ModelAndView getTeaStu(){
		mv.setViewName("person/tea/TeaStu");
		return mv;
	}
	@RequestMapping(value="/manage/stuStatistics",method=RequestMethod.GET)
	public ModelAndView getStatistics(){
		mv.setViewName("person/tea/manage/stuStatistics");
		return mv;
	}
	@RequestMapping(value="/manage/teaStatistics",method=RequestMethod.GET)
	public ModelAndView getteaStatistics(){
		mv.setViewName("person/tea/manage/teaStatistics");
		return mv;
	}
	@RequestMapping(value="/manage/Report",method=RequestMethod.GET)
	public ModelAndView getReport(){
		mv.setViewName("person/tea/manage/Report");
		return mv;
	}
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public ModelAndView teaInfo1(@PathVariable("id") String id){
		//01博士 11硕士 20本科
		mv.setViewName("person/tea/TeaInfoAll");//"+id);
		mv.addObject("id", id);
		return mv;
	}

}
