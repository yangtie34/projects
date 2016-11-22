package com.jhnu.product.manager.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.jhnu.product.common.school.service.DeptPermissionService;
import com.jhnu.product.manager.school.service.DeptService;
import com.jhnu.product.manager.student.service.IInternalStuInfoService;
import com.jhnu.product.manager.student.service.StuScoreService;

@Controller
@RequestMapping("/manager/student")
public class StuInfoController {

	@Autowired
	private IInternalStuInfoService internalStuInfoService;
	@Autowired
	private DeptPermissionService dataPermissionService;
	@Autowired
	private DeptService deptService;

	@Autowired
	private StuScoreService stuScoreService;

	@RequestMapping(method = RequestMethod.GET, value = "/stu")
	public ModelAndView getStuDept() {
		ModelAndView mv = new ModelAndView("/manager/student/stuinfo");
		// HttpSession session = ContextHolderUtils.getSession();
		// User user=(User)session.getAttribute(Globals.USER_SESSION);
		// 获取数据权限
		// String ids = dataPermissionService.getDeptTeachPermsStrByUserId(user.getId());
		String ids = "0";
		mv.addObject("dept_id", ids);
		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/stufrom")
	public ModelAndView getStuFrom() {
		ModelAndView mv = new ModelAndView("/manager/student/stufrom");
		String ids = "0";
		mv.addObject("dept_id", ids);
		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/stu/stubreakfastscore")
	public ModelAndView getStusBreakfastAndScore() {
		ModelAndView mv = new ModelAndView("/manager/student/stubreakfastscore");
		// HttpSession session = ContextHolderUtils.getSession();
		// User user=(User)session.getAttribute(Globals.USER_SESSION);
		// 获取数据权限
		// String ids = dataPermissionService.getDeptTeachPermsStrByUserId(user.getId());
		String ids = "0";
		mv.addObject("dept_id", ids);
		return mv;
	}

	// TODO 跳转到学生成绩统计页面
	@RequestMapping(method = RequestMethod.GET, value = "/source")
	public ModelAndView getStuSource() {
		ModelAndView mv = new ModelAndView("/manager/student/stuScore");
		// HttpSession session = ContextHolderUtils.getSession();
		// User user=(User)session.getAttribute(Globals.USER_SESSION);
		// 获取数据权限
		// String ids = dataPermissionService.getDeptTeachPermsStrByUserId(user.getId());
		String dept_id = "0";
//		String school_year = "2012-2013";
//		String term_code = "01";
		// List<Map<String, Object>> deptScores = stuScoreService.getTermScoreByDept(dept_id);
		// 时间格式为： 2012-2013 term_code 值为 01
		// List<Map<String, Object>> scoreLog = stuScoreService.getScoreLog(dept_id, false, school_year, term_code);
		mv.addObject("dept_id", dept_id);
		// mv.addObject("deptScores",deptScores);
		// mv.addObject("scoreLog", scoreLog);

		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/stu/libraryscore")
	public ModelAndView getStusLibraryScore() {
		ModelAndView mv = new ModelAndView("/manager/student/stuslibraryscore");
		// HttpSession session = ContextHolderUtils.getSession();
		// User user=(User)session.getAttribute(Globals.USER_SESSION);
		// 获取数据权限
		// String ids = dataPermissionService.getDeptTeachPermsStrByUserId(user.getId());
		String ids = "0";
		mv.addObject("dept_id", ids);
		return mv;
	}

	/**
	 * 跳转到学生消费分析页面
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/stu/conHabit")
	public ModelAndView getStuConHabit() {
		ModelAndView mv = new ModelAndView("/manager/student/stu_consumption_habit");
		// HttpSession session = ContextHolderUtils.getSession();
		// User user=(User)session.getAttribute(Globals.USER_SESSION);
		// 获取数据权限
		// String ids = dataPermissionService.getDeptTeachPermsStrByUserId(user.getId());
		String ids = "0";
		mv.addObject("dept_id", ids);
		return mv;
	}

	/**
	 * 跳转到低消费学生名单
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/stu/lowCon")
	public ModelAndView getStuLowConsumption() {
		ModelAndView mv = new ModelAndView("/manager/student/stu_low_consumption");
		// HttpSession session = ContextHolderUtils.getSession();
		// User user=(User)session.getAttribute(Globals.USER_SESSION);
		// 获取数据权限
		// String ids = dataPermissionService.getDeptTeachPermsStrByUserId(user.getId());
		String ids = "0";
		mv.addObject("dept_id", ids);
		return mv;
	}

}
