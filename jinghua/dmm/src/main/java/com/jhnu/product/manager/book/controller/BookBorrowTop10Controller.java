package com.jhnu.product.manager.book.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.product.common.school.service.DeptPermissionService;
import com.jhnu.product.manager.book.service.BookBorrowTop10Service;
import com.jhnu.product.manager.school.service.DeptService;

@Controller
@RequestMapping("/manager/book")
public class BookBorrowTop10Controller {
	
	@Autowired
	private DeptPermissionService dataPermissionService;
	@Autowired
	private DeptService deptService;
	@Autowired
	private BookBorrowTop10Service bookBorrowTop10Service;
	
	
	@RequestMapping(method=RequestMethod.GET,value="/borrowTop10")
	public ModelAndView getBookTop10(){
		ModelAndView mv = new ModelAndView("/manager/book/bookTop10") ;
		//HttpSession session = ContextHolderUtils.getSession();
		//User user=(User)session.getAttribute(Globals.USER_SESSION);
		//获取数据权限
		//String ids = dataPermissionService.getDeptTeachPermsStrByUserId(user.getId());
		String ids = "0";
		mv.addObject("dept_id", ids);
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/borrowTop10/stu")
	public ModelAndView getBookTop10Stu(@RequestParam("deptId") String deptId,@RequestParam("isLeaf") boolean isLeaf
			,@RequestParam("bookId") String bookId,@RequestParam("bookName")  String bookName
			,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate){
		ModelAndView mv = new ModelAndView("/manager/book/bookTop10stu") ;
		mv.addObject("deptId", deptId);
		mv.addObject("isLeaf", isLeaf);
		mv.addObject("bookId", bookId);
		mv.addObject("bookName", bookName);
		mv.addObject("startDate", startDate);
		mv.addObject("endDate", endDate);
		return mv;
	}
	

	@RequestMapping(method=RequestMethod.GET,value="/borrowTop10/book")
	public ModelAndView getBookTop10Book(@RequestParam("stuId") String stuId,@RequestParam("stuName") String stuName
			,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate){
		ModelAndView mv = new ModelAndView("/manager/book/bookTop10book") ;
		mv.addObject("stuId", stuId);
		mv.addObject("stuName", stuName);
		mv.addObject("startDate", startDate);
		mv.addObject("endDate", endDate);
		return mv;
	}
}
