package com.jhnu.product.manager.card.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.product.common.school.service.DeptPermissionService;
import com.jhnu.product.manager.school.service.DeptService;

@Controller
@RequestMapping("/manager/card")
public class CardController {
	
	@Autowired
	private DeptPermissionService dataPermissionService;
	@Autowired
	private DeptService deptService;

	@RequestMapping(method = RequestMethod.GET, value = "/cptbt")
	public ModelAndView getTeaDept() {
		ModelAndView mv = new ModelAndView("/manager/card/cardPayTypeByTime");
		// HttpSession session = ContextHolderUtils.getSession();
		// User user=(User)session.getAttribute(Globals.USER_SESSION);
		// 获取数据权限
		// String ids = dataPermissionService.getDeptTeachPermsStrByUserId(user.getId());
		String ids = "0";
		List<Map<String, Object>> depts = deptService.getDept(ids);
		mv.addObject("depts", depts);
		mv.addObject("ids", ids);
		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/cptbt/{dept_id}")
	public ModelAndView getTeaDeptLeaf(@PathVariable("dept_id") String dept_id) {
		ModelAndView mv = new ModelAndView("/manager/card/cardPayTypeByTime");
		String ids = dept_id;
		List<Map<String, Object>> depts = deptService.getDeptLeaf(dept_id);
		mv.addObject("depts", depts);
		mv.addObject("ids", ids);
		return mv;
	}

}
