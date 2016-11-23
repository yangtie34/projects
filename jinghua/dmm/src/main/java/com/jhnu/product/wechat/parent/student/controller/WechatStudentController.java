package com.jhnu.product.wechat.parent.student.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.product.wechat.parent.band.service.BandService;
import com.jhnu.product.wechat.parent.student.service.WechatStudentService;
import com.jhnu.product.wechat.util.WechatUtil;
import com.jhnu.util.common.DateUtils;


@Controller
@RequestMapping("/wechat/parent")
public class WechatStudentController {
	
    @Autowired
	private WechatStudentService studentService;
	@Autowired
    private BandService bandService;
	

	@RequestMapping(method=RequestMethod.GET,value="/{is_wechat}/student")
	public ModelAndView getStuPage(@PathVariable("is_wechat") String is_wechat){
		ModelAndView mv=new ModelAndView("/wechat/parent/wechat");
		mv.addObject("url", "/wechat/parent/"+is_wechat+"/student");
		mv.addObject("is_wechat", WechatUtil.isWechat(is_wechat));
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{is_wechat}/student/{code}")
	public ModelAndView getStuInfo(@PathVariable("is_wechat") String is_wechat,@PathVariable("code") String code){
		ModelAndView mv ;
		String stuId="";
		boolean isWechat=WechatUtil.isWechat(is_wechat);
		if(isWechat){
			stuId=bandService.getStuIdByWechatCode(code);
		}else{
			stuId=code;
		}
		if(stuId.equals("")&&isWechat){
			mv=new ModelAndView("redirect:/wechat/parent/band");
		}else if(stuId.equals("error")&&isWechat){
			mv=new ModelAndView("redirect:/wechat/parent/student");
		}else{
			int way = 0;
			if(isWechat){
				way = 1;
			}
			bandService.addVisitLogging("03", way, DateUtils.getNowDate2(), stuId);
			
			List<Map<String,Object>> stuList = studentService.getStudentInfo(stuId);
			List<Map<String,Object>> dormList = studentService.getDormInfo(stuId);
			List<Map<String,Object>> deptList = studentService.getDeptInfo();
			List<Map<String,Object>> roomList = studentService.getRoommate(stuId);
			List<Map<String,Object>> awardList = studentService.getAward(stuId);
			List<Map<String,Object>> subsidyList = studentService.getSubsidy(stuId);
			
			mv = new ModelAndView("/wechat/parent/student/stu");
			if(stuList.size()>0){
				mv.addObject("stu", stuList.get(0));
			}
			if(dormList.size()>0){
				mv.addObject("dorm", dormList.get(0));
			}
			if(deptList.size()>0){
				mv.addObject("deptList", deptList);
			}
			if(roomList.size()>0){
				mv.addObject("roomList", roomList);
			}
			if(awardList.size()>0){
				mv.addObject("awardList", awardList);
			}
			if(subsidyList.size()>0){
				mv.addObject("subsidyList", subsidyList);
			}
			
			mv.addObject("stuId", stuId);
			mv.addObject("is_wechat", isWechat);
		}
		return mv;
	}
	
}
