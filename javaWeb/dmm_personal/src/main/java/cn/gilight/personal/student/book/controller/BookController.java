package cn.gilight.personal.student.book.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhnu.syspermiss.util.ContextHolderUtils;
import cn.gilight.framework.page.Page;
import cn.gilight.personal.student.book.service.BookService;
import cn.gilight.personal.teacher.main.controller.TeacherMainController;

@Controller("bookController")
@RequestMapping("/student/book")
public class BookController {
	private Logger log = Logger.getLogger(TeacherMainController.class);
	@Autowired
	private BookService bookService;
	
	@RequestMapping("/borrowCounts")
	@ResponseBody
	public int getBorrowCounts(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return bookService.getBorrowCounts(username);
	}
	
	@RequestMapping("/borrowProportion")
	@ResponseBody
	public Map<String,Object> getBorrowProportion(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return bookService.getBorrowProportion(username);
	}
	
	@RequestMapping("/recommendBook")
	@ResponseBody
	public List<Map<String,Object>> getRecommendBook(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return bookService.getRecommendBook(username);
	}
	
	@RequestMapping("/borrowList")
	@ResponseBody
	public Page getBorrowList(String currpage){
		Page page = new Page();
		page.setCurpage(Integer.parseInt(currpage));
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return bookService.getBorrowList(username,page);
	}
	
	@RequestMapping("/borrowType")
	@ResponseBody
	public List<Map<String,Object>> getBorrowType(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return bookService.getBorrowType(username);
	}
	
	
}
