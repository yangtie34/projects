package cn.gilight.product.card.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.gilight.framework.code.Code;

@Controller
@RequestMapping("card")
public class CardController {
	@RequestMapping(value="/recharge",method=RequestMethod.GET)
	public ModelAndView recharge(){
		ModelAndView mv=new ModelAndView("/card/recharge");
		return mv;
	}
	@RequestMapping(value="/cardEmploy",method=RequestMethod.GET)
	public ModelAndView cardEmploy(){
		ModelAndView mv=new ModelAndView("/card/cardEmploy");
		mv.addObject("cardUsed", Code.getKey("card.uesd"));
		return mv;
	}
	@RequestMapping(value="/fxByXflx",method=RequestMethod.GET)
	public ModelAndView fxByXflx(){
		ModelAndView mv=new ModelAndView("/card/fxByXflx");
		return mv;
	}
	@RequestMapping(value="/fxBytype",method=RequestMethod.GET)
	public ModelAndView fxBytype(){
		ModelAndView mv=new ModelAndView("/card/fxBytype");
		return mv;
	}
	@RequestMapping(value="/payAbility",method=RequestMethod.GET)
	public ModelAndView payAbility(){
		ModelAndView mv=new ModelAndView("/card/payAbility");
		return mv;
	}
	@RequestMapping(value="/byPort",method=RequestMethod.GET)
	public ModelAndView byPort(){
		ModelAndView mv=new ModelAndView("/card/byPort");
		return mv;
	}
	@RequestMapping(value="/payHot",method=RequestMethod.GET)
	public ModelAndView payHot(){
		ModelAndView mv=new ModelAndView("/card/payHot");
		return mv;
	}
}
