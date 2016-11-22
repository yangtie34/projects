package cn.gilight.personal.student.concat.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhnu.syspermiss.util.ContextHolderUtils;
import cn.gilight.personal.student.concat.service.ConcatService;
import cn.gilight.personal.teacher.main.controller.TeacherMainController;

@Controller("concatController")
@RequestMapping("/student/concat")
public class ConcatController {
	private Logger log = Logger.getLogger(TeacherMainController.class);
	@Autowired
	private ConcatService concatService;
	
	@RequestMapping("/concat")
	@ResponseBody
	public List<Map<String,Object>> getConcat(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return concatService.getConcat(username, "");
	}
	
	@RequestMapping("/concatParam")
	@ResponseBody
	public List<Map<String,Object>> getConcatParam(String param){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return concatService.getConcat(username, param);
	}
	
	
	
	
}
