package com.jhnu.product.warn.score.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.product.warn.score.service.ScoreWarnService;

@Controller
@RequestMapping("/warn")
public class ScoreWarnController {

	@Autowired
	private ScoreWarnService scoreWarnService;
	
	@RequestMapping(method=RequestMethod.GET,value="/score/{stuId}")
	public ModelAndView getScoreWarn(@PathVariable String stuId){
		List<Map<String,Object>> scoreWarn = scoreWarnService.getScores(stuId);
		ModelAndView mv = new ModelAndView("/warn/scoreWarn");
		mv.addObject("scoreWarn", scoreWarn);
		mv.addObject("counts", scoreWarn.size());
		return mv;
	}

}
