package com.jhnu.system.permiss.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.framework.entity.ResultBean;
import com.jhnu.system.permiss.service.ResourcesService;
import com.jhnu.system.permiss.service.UserService;
import com.jhnu.util.common.PropertiesUtils;
import com.jhnu.util.common.UserUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private ResourcesService resourcesService;
	
	@RequestMapping(method=RequestMethod.GET, value="/nopermi")
	public ModelAndView noPermi(){
		ModelAndView mv=new ModelAndView("/unauthorized");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="/changepwd",method=RequestMethod.POST)
	public ResultBean changePwd(@RequestParam("username") String username,
			@RequestParam("oldpwd") String oldpwd,
			@RequestParam("newpwd") String newpwd){
		ResultBean rb=new ResultBean();
		rb=userService.updateUserpwdAjax(username, oldpwd, newpwd);
		return rb;
	}
	
	@ResponseBody
	@RequestMapping(value="/checkuser",method=RequestMethod.POST)
	public ResultBean checkUserName(@RequestParam("username") String username){
		ResultBean rb=new ResultBean();
		if(userService.findByUsername(username)!=null){
			rb.setTrue(true);
		}else{
			rb.setTrue(false);
		}
		return rb;
	}
	
	@RequestMapping(value="/changepwd/page",method=RequestMethod.GET)
	public ModelAndView changePwd(){
		ModelAndView mv=new ModelAndView("/system/permiss/changepwd");
		mv.addObject("casLoginUrl", PropertiesUtils.getDBPropertiesByName("sys.casServerUrl"));
		return mv;
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView getuserList(){
		ModelAndView mv=new ModelAndView("/system/permiss/user");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="/getphoto",method=RequestMethod.GET)
	public void getUserPhoto(HttpServletRequest request,HttpServletResponse response) {
		OutputStream os = null;
		byte[] photo = UserUtil.getPhotoByUserName(UserUtil.getCasLoginName());
		response.setContentType("text/html");
		try {
			os = response.getOutputStream();
			os.write(photo);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
