package cn.gilight.dmm.teaching.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("scoreBook")
public class ScoreBookCtol {
	@RequestMapping()
	public String init(){
		return "scoreBook";
	}
}
