package cn.gilight.product.equipment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("equipment")
public class EquipmentController {
	@RequestMapping(value="/Info",method=RequestMethod.GET)
	public ModelAndView Info(){
		ModelAndView mv=new ModelAndView("/equipment/Info");
		return mv;
	}
	@RequestMapping(value="/Admins",method=RequestMethod.GET)
	public ModelAndView admins(){
		ModelAndView mv=new ModelAndView("/equipment/Admins");
		mv.addObject("sjcode", "all");
		return mv;
	}
	@RequestMapping(value="/Overdue",method=RequestMethod.GET)
	public ModelAndView overdue(){
		ModelAndView mv=new ModelAndView("/equipment/Overdue");
		return mv;
	}
}
