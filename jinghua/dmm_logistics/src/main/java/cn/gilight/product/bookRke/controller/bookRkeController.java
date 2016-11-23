package cn.gilight.product.bookRke.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("bookRke")
public class bookRkeController {
	@RequestMapping(value="/LibraryRke",method=RequestMethod.GET)
	public ModelAndView goLibraryRke(){
		ModelAndView mv=new ModelAndView("/bookRke/LibraryRke");
		return mv;
	}
	@RequestMapping(value="/StuBookRke",method=RequestMethod.GET)
	public ModelAndView goStuBookRke(){
		ModelAndView mv=new ModelAndView("/bookRke/StuBookRke");
		return mv;
	}
	
}
 