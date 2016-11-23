package com.jhnu.product.wechat.parent.band.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.jhnu.product.common.stu.entity.Student;
import com.jhnu.product.common.stu.service.StuService;
import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.product.wechat.parent.band.entity.Band;
import com.jhnu.product.wechat.parent.band.entity.WechatPwd;
import com.jhnu.product.wechat.parent.band.service.BandService;
import com.jhnu.product.wechat.parent.band.service.WechatPwdService;
import com.jhnu.product.wechat.parent.menu.entity.WechatMenu;
import com.jhnu.product.wechat.parent.menu.service.WechatMenuService;
import com.jhnu.product.wechat.parent.warn.service.WechatWarnService;
import com.jhnu.product.wechat.util.WechatUtil;
import com.jhnu.util.common.DateUtils;
import com.jhnu.util.common.HttpUtil;

@Controller
@RequestMapping("/wechat/parent")
public class BandController {
	
	@Autowired
	private BandService bandService;
	@Autowired
	private WechatPwdService wechatPwdService;
	@Autowired
	private WechatMenuService wechatMenuService;
	@Autowired
	private WechatWarnService wechatWarnService;
	@Autowired
	private StuService stuService;
	
	
	@RequestMapping(method=RequestMethod.GET,value="/{is_wechat}/main")
	public ModelAndView getBandList(@PathVariable("is_wechat") String is_wechat){
		ModelAndView mv=new ModelAndView("/wechat/parent/wechat");
		mv.addObject("url", "/wechat/parent/"+is_wechat+"/main");
		mv.addObject("is_wechat", WechatUtil.isWechat(is_wechat));
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{is_wechat}/main/{wechat_code}")
	public ModelAndView getBandList(@PathVariable("is_wechat") String is_wechat,@PathVariable("wechat_code") String wechat_code){
		ModelAndView mv;
		String stuId="";
		boolean isWechat=WechatUtil.isWechat(is_wechat);
		if(isWechat){
			stuId=bandService.getStuIdByWechatCode(wechat_code);
		}else{
			stuId=wechat_code;
		}
		if(stuId.equals("")&&isWechat){
			mv=new ModelAndView("redirect:/wechat/parent/band");
		}else if(stuId.equals("error")&&isWechat){
			mv=new ModelAndView("redirect:/wechat/parent/true/main");
		}else{
			int way = 0;
			if(isWechat){
				way = 1;
			}
			bandService.addVisitLogging("02", way, DateUtils.getNowDate2(), stuId);
			WechatMenu wechatMenu=new WechatMenu(1);
			wechatMenu.setLevel_(1);
			List<WechatMenu> menus=wechatMenuService.getWechatMenuByThis(wechatMenu);
			mv=new ModelAndView("/wechat/parent/main");
			mv.addObject("menus", menus);
			Student stu=stuService.getStudentInfo(stuId);
			mv.addObject("stu", stu);
			//未读信息数量
			int count= wechatWarnService.countNoReadWarns(stuId);
			mv.addObject("warncount", count);
			
			mv.addObject("is_wechat", isWechat);
		}
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/band")
	public ModelAndView getband(){
		ModelAndView mv=new ModelAndView("/wechat/parent/band/band");
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/band")
	public ModelAndView addband(@RequestParam("stuIdNo") String stuIdNo,@RequestParam("password") String password,@RequestParam("w") String wechat_code,RedirectAttributes ra){
		ModelAndView mv;
		Band band=new Band();
		band.setStu_idno(stuIdNo);
		JSONObject result  = HttpUtil.getWechatOpenidByCode(wechat_code);
		if(result.get("openid")==null){
			mv=new ModelAndView("redirect:/wechat/parent/band");
			return mv;
		}
		band.setWeChat_no(result.get("openid").toString());
		ResultBean rb=bandService.addBandByPassword(band,password);
		if(rb.getIsTrue()){
			if(wechatPwdService.checkPwdIsChange(band.getStu_id())){
				mv=new ModelAndView("redirect:/wechat/parent/true/main");
			}else{
				mv=new ModelAndView("redirect:/wechat/parent/pwd");
			}
		}else{
			mv=new ModelAndView("redirect:/wechat/parent/band");
			ra.addAttribute("msg", "错误");//此处msg的值只在乎有没有，不在乎内容，提示内容移至前台控制
		}
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/loginout")
	public ModelAndView loginout(){
		ModelAndView mv=new ModelAndView("/wechat/parent/wechat");
		mv.addObject("url", "/wechat/parent/loginout");
		mv.addObject("is_wechat", true);
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/loginout/{wechat_code}")
	public ModelAndView loginout(@PathVariable String wechat_code){
		ModelAndView mv=new ModelAndView("redirect:/wechat/parent/band");
		String stuId=bandService.getStuIdByWechatCode(wechat_code);
		Band band=new Band();
		band.setStu_id(stuId);
		bandService.removeBand(band);
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/pwd")
	public ModelAndView getpwdPage(){
		ModelAndView mv=new ModelAndView("/wechat/parent/band/pwd");
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/pwd")
	public ModelAndView changePwd(@RequestParam("phone") String phone,@RequestParam("password") String password,@RequestParam("w") String wechat_code){
		ModelAndView mv;
		String stuId=bandService.getStuIdByWechatCode(wechat_code);
		if(stuId.equals("")){
			mv=new ModelAndView("redirect:/wechat/parent/band");
		}else if(stuId.equals("error")){
			mv=new ModelAndView("redirect:/wechat/parent/pwd");
		}else{
			WechatPwd pwd=new WechatPwd(stuId,password,phone);
			wechatPwdService.changeWeChatPassword(pwd);
			mv=new ModelAndView("redirect:/wechat/parent/true/main");
		}
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/lostpwd")
	public ModelAndView getlostpwd(){
		ModelAndView mv=new ModelAndView("/wechat/parent/band/lostpwd");
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/lostpwd")
	public ModelAndView resetPwd(@RequestParam("phone") String phone,@RequestParam("stuIdNo") String stuIdNo,@RequestParam("w") String wechat_code,RedirectAttributes ra){
		ModelAndView mv;
		Student stu=stuService.getStudentInfoByIdno(stuIdNo);
		if(stu!=null && wechatPwdService.checkWechatPhone(stu.getNo_(),phone)){
			Band band=new Band();
			band.setStu_idno(stuIdNo);
			JSONObject result = HttpUtil.getWechatOpenidByCode(wechat_code);
			if(result.get("openid")==null){
				mv=new ModelAndView("redirect:/wechat/parent/lostpwd");
				return mv;
			}
			band.setWeChat_no(result.get("openid").toString());
			bandService.addBand(band);
			mv=new ModelAndView("redirect:/wechat/parent/pwd");
		}else{
			mv=new ModelAndView("redirect:/wechat/parent/lostpwd");
			ra.addAttribute("msg", "错误"); //此处msg的值只在乎有没有，不在乎内容，提示内容移至前台控制
		}
		return mv;
	}
	
}
