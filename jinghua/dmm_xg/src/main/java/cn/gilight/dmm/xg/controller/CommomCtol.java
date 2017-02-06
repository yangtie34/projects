package cn.gilight.dmm.xg.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 公共ctol
 * 
 * @author xuebl
 * @date 2016年6月20日 上午9:11:21
 */
@Controller
@RequestMapping("XgCommon")
public class CommomCtol {

	
	public void validSession(HttpServletResponse resp){
		resp.setStatus(404);
	}
	
}
