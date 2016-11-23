package cn.gilight.product.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("book")
public class BookController {
	@RequestMapping(value="/Info",method=RequestMethod.GET)
	public ModelAndView Info(){
		ModelAndView mv=new ModelAndView("/book/Info");
		return mv;
	}
	@RequestMapping(value="/Read",method=RequestMethod.GET)
	public ModelAndView read(){
		ModelAndView mv=new ModelAndView("/book/Read");
		return mv;
	}
	@RequestMapping(value="/ReadStu",method=RequestMethod.GET)
	public ModelAndView readStu(){
		ModelAndView mv=new ModelAndView("/book/ReadStu");
		return mv;
	}
	@RequestMapping(value="/Overdue",method=RequestMethod.GET)
	public ModelAndView overdue(){
		ModelAndView mv=new ModelAndView("/book/Overdue");
		return mv;
	}
	@RequestMapping(value="/OverdueStu",method=RequestMethod.GET)
	public ModelAndView overdueStu(){
		ModelAndView mv=new ModelAndView("/book/OverdueStu");
		return mv;
	}
	@RequestMapping(value="/ReadRank",method=RequestMethod.GET)
	public ModelAndView readRank(){
		ModelAndView mv=new ModelAndView("/book/ReadRank");
		return mv;
	}
	@RequestMapping(value="/OverdueRank",method=RequestMethod.GET)
	public ModelAndView overdueRank(){
		ModelAndView mv=new ModelAndView("/book/OverdueRank");
		return mv;
	}
}
