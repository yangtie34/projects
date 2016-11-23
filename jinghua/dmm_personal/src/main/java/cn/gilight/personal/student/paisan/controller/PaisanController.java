package cn.gilight.personal.student.paisan.controller;


import java.util.Map;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhnu.syspermiss.util.ContextHolderUtils;
import cn.gilight.framework.page.Page;
import cn.gilight.personal.student.paisan.service.PaisanService;
import cn.gilight.personal.teacher.main.controller.TeacherMainController;

@Controller("paisanController")
@RequestMapping("/student/paisan")
public class PaisanController {
	private Logger log = Logger.getLogger(TeacherMainController.class);
	@Autowired
	private PaisanService paisanService;
	
	@RequestMapping("/paisan")
	@ResponseBody
	public Page getPaisan(){
		Page page = new Page();
		page.setCurpage(1);
		String stuname = "";
		String flag = "";
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return paisanService.getPaisan(username,stuname,flag,page);
	}
	
	@RequestMapping("/paisanParam")
	@ResponseBody
	public Page getPaisanParam(String currpage,String stuname,String flag){
		Page page = new Page();
		page.setCurpage(Integer.parseInt(currpage));
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return paisanService.getPaisan(username,stuname,flag,page);
	}
	
	@RequestMapping("/paisanStu")
	@ResponseBody
	public Map<String,Object> getPaisanStu(String stu_id){
		return paisanService.getPaisanStu(stu_id);
	}
	
	
}
