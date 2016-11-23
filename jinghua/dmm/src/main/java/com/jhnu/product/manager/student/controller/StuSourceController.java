package com.jhnu.product.manager.student.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.jhnu.product.common.school.service.DeptPermissionService;
import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.product.manager.school.service.DeptService;
import com.jhnu.product.manager.student.service.IInternalStuInfoService;

@Controller
@RequestMapping("/manager/stuSource")
public class StuSourceController {
	
	@Autowired
	private IInternalStuInfoService internalStuInfoService;
	@Autowired
	private DeptPermissionService dataPermissionService;
	@Autowired
	private DeptService deptService;
	
	
	@RequestMapping(method=RequestMethod.GET,value="/source")
	public ModelAndView getStuDept(){
		ModelAndView mv = new ModelAndView("/manager/student/stusource") ;
		//HttpSession session = ContextHolderUtils.getSession();
		//User user=(User)session.getAttribute(Globals.USER_SESSION);
		//获取数据权限
		//String ids = dataPermissionService.getDeptTeachPermsStrByUserId(user.getId());
		String ids = "0";
		List<Map<String,Object>> depts = deptService.getDeptTeach(ids);
		List<Map<String,Object>> edus = internalStuInfoService.typeNumsOfStus(ids, false);
		mv.addObject("depts", depts);
		mv.addObject("edus", edus);
		mv.addObject("dept_id", ids);
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/source/{dept_id}")
	public ModelAndView getStuMajor(@PathVariable("dept_id") String dept_id){
		ModelAndView mv = new ModelAndView("/manager/student/stusource") ;
		String ids = dept_id;
		List<Map<String,Object>> depts = deptService.getMajorTeach(dept_id);
		mv.addObject("depts", depts);
		mv.addObject("dept_id", ids);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST,value="/stu")
	public ResultBean getTest() {
		ResultBean resultBean = new ResultBean();
		List<Map<String, Object>> x = internalStuInfoService.stusSexComposition("310", "0", false);
		resultBean.setObject(x);
		return resultBean;
	}
	

}
