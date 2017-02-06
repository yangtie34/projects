package cn.gilight.dmm.teaching.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("scoreBreakfast")
public class ScoreBreakfastCtol {
	@RequestMapping()
	public String init(){
		return "scoreBreakfast";
	}
}
