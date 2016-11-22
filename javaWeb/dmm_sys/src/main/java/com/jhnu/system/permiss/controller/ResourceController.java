package com.jhnu.system.permiss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.system.common.code.entity.Code;
import com.jhnu.system.common.code.service.CodeService;

@Controller
@RequestMapping("/system/user")
public class ResourceController {
	
	@Autowired
	private CodeService coedService;

	@RequestMapping(value="/resource",method=RequestMethod.GET)
	public ModelAndView getuserList2(){
		ModelAndView mv=new ModelAndView("/system/permiss/resource");
		Code code=new Code();
		code.setIstrue(1);
		code.setCode_type("RESOURCE_TYPE");
		List<Code> codes=coedService.getCode(code);
		mv.addObject("resTypes", codes);
		return mv;
	}
}
