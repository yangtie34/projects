package com.jhnu.product.manager.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.jhnu.product.common.school.service.DeptPermissionService;
import com.jhnu.product.manager.school.service.DeptService;
import com.jhnu.product.manager.student.service.StuTop5Service;

@Controller
@RequestMapping("/manager/score")
public class ScoreTop5Controller {
	
	@Autowired
	private DeptPermissionService dataPermissionService;
	@Autowired
	private DeptService deptService;
	@Autowired
	private StuTop5Service stuTop5Service;
	
	
	@RequestMapping(method=RequestMethod.GET,value="/scoreTop5")
	public ModelAndView getStuDept(){
		ModelAndView mv = new ModelAndView("/manager/score/scoreTop5") ;
		//HttpSession session = ContextHolderUtils.getSession();
		//User user=(User)session.getAttribute(Globals.USER_SESSION);
		//获取数据权限
		//String ids = dataPermissionService.getDeptTeachPermsStrByUserId(user.getId());
		String ids = "0";
		mv.addObject("dept_id", ids);
		return mv;
	}
}
