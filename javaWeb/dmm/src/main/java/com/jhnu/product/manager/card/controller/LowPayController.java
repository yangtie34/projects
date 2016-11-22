package com.jhnu.product.manager.card.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 学生低消费控制器
 * 
 * @author baihy
 * 
 */
@Controller
@RequestMapping(value = "/manager/lowPay")
public class LowPayController {

	/**
	 * 跳转到低消费学生详情页面
	 * 
	 * @param stu_id
	 * @return
	 */
	@RequestMapping(value = "stu/deatil")
	public ModelAndView redirectLowPayDetail(String stu_id, String stu_sex) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("stu_id", stu_id);
		mv.addObject("stu_sex", stu_sex);
		mv.setViewName("/manager/student/stu_low_detail");
		return mv;
	}

}
