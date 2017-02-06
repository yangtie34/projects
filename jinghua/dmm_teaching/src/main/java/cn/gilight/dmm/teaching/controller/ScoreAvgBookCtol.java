package cn.gilight.dmm.teaching.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("scoreAvgBook")
public class ScoreAvgBookCtol {
	@RequestMapping()
	public String init(){
		return "scoreAvgBook";
	}
}
