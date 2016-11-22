package cn.gilight.product.net.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("net")
public class NetController {
	@RequestMapping(value="/netStu",method=RequestMethod.GET)
	public ModelAndView netStu(){
		ModelAndView mv=new ModelAndView("/net/netStu");
		return mv;
	}
	@RequestMapping(value="/netStuWarn",method=RequestMethod.GET)
	public ModelAndView netStuWarn(){
		ModelAndView mv=new ModelAndView("/net/netStuWarn");
		return mv;
	}
	@RequestMapping(value="/netStuDept",method=RequestMethod.GET)
	public ModelAndView netStuDept(){
		ModelAndView mv=new ModelAndView("/net/netStuDept");
		return mv;
	}
	@RequestMapping(value="/netType",method=RequestMethod.GET)
	public ModelAndView netType(){
		ModelAndView mv=new ModelAndView("/net/netType");
		return mv;
	}
	@RequestMapping(value="/netTeaWarn",method=RequestMethod.GET)
	public ModelAndView netTeaWarn(){
		ModelAndView mv=new ModelAndView("/net/netTeaWarn");
		return mv;
	}
	@RequestMapping(value="/netTeaRank",method=RequestMethod.GET)
	public ModelAndView netTeaRank(){
		ModelAndView mv=new ModelAndView("/net/netTeaRank");
		return mv;
	}
}
 