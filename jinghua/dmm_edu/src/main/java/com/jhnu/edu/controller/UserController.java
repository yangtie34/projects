package com.jhnu.edu.controller;


import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.edu.entity.User;
import com.jhnu.edu.service.UserService;
import com.jhnu.framework.util.common.ContextHolderUtils;
import com.jhnu.framework.util.common.HttpRequestDeviceUtils;
import com.jhnu.framework.util.product.Globals;


@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(method=RequestMethod.POST, value="/login")
	public ModelAndView loginUser(@RequestParam("username") String username,@RequestParam("password") String password){
		ModelAndView mv=new ModelAndView("redirect:/edu/index");
		ModelAndView errorMv = new ModelAndView("/login");
		Subject subject = SecurityUtils.getSubject();
		if(password==null || password.length()>20){
			errorMv.addObject("error", "密码错误");
			return errorMv;
		}
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
        	subject.login(token);
		}catch (UnknownAccountException e) {
			errorMv.addObject("error", "账号不存在！");
			return errorMv;
		}catch (LockedAccountException e) {
			errorMv.addObject("error", "您的账号暂时被锁定！");
			return errorMv;
		}catch (AuthenticationException e) {
			errorMv.addObject("error", "密码错误！");
			return errorMv;
		}
        HttpSession session = ContextHolderUtils.getSession();
        //userService.addUserLogging(username, HttpRequestDeviceUtils.getLoginWay(ContextHolderUtils.getRequest()));
			session.setAttribute(Globals.USER_SESSION,  userService.findByUsername(username));
        	//mv=new ModelAndView("redirect:/four/"+username);
	/*	User user=new User();
		user.setUsername(username);
		Resources resource=resourcesService.getMainPageByUser(user);
		if(resource!=null){
			mv=new ModelAndView("redirect:"+resource.getUrl_());
		}else{
			mv=new ModelAndView("/unauthorized");
		}*/
		return mv;
	}
	/*
	@RequestMapping(method=RequestMethod.GET, value="/ssoLogin/{id}/{key}")
	public ModelAndView ssoLoginUser(@PathVariable("id") String username,@PathVariable("key") String key){
		HttpSession session = ContextHolderUtils.getSession();
		ModelAndView mv=new ModelAndView("redirect:/four/"+username);
		
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String is_config = session.getServletContext().getRealPath("/client.properties");
		Cookie all_cookies[] = request.getCookies();
		Cookie myCookie;
		String decodedCookieValue = null;
		try{
			if (all_cookies != null) {
				for (int i = 0; i < all_cookies.length; i++) {
					myCookie = all_cookies[i];
					if (myCookie.getName().equals("iPlanetDirectoryPro")) {
						decodedCookieValue = URLDecoder.decode(myCookie.getValue(), "GB2312");
					}
				}
			}
			if(decodedCookieValue==null){
				mv=new ModelAndView("/login");
				mv.addObject("error", "单点登录认证失败");
				return mv;
			}
			IdentityFactory factory = IdentityFactory.createFactory(is_config);
			IdentityManager im = factory.getIdentityManager();
			username = im.getCurrentUser(decodedCookieValue);
		}catch(Exception e){
			mv=new ModelAndView("/login");
			mv.addObject("error", "单点登录认证失败");
			return mv;
		}
		
		Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, "012345678901234567890");
        
        if(!"mobile".equals(key)){
			mv=new ModelAndView("/login");
			mv.addObject("error", "单点登录认证失败！");
		}
        try {
        	subject.login(token);
		}catch (UnknownAccountException e) {
			mv=new ModelAndView("/login");
			mv.addObject("error", "账号不存在！");
		}catch (LockedAccountException e) {
			mv=new ModelAndView("/login");
			mv.addObject("error", "您的账号暂时被锁定！");
		}catch (AuthenticationException e) {
			mv= new ModelAndView("/login");
			mv.addObject("error", "密码错误！");
		}
        
        session.setAttribute(Globals.USER_SESSION,  userService.findByUsername(username));
        session.setAttribute(Globals.USER_EXPAND_INFO,  userService.getUserExpandInfo(username));
        userService.addUserLogging(username, HttpRequestDeviceUtils.getLoginWay(ContextHolderUtils.getRequest()));
		return mv;
	}
	*/
	@RequestMapping(method=RequestMethod.GET, value="/login")
	public ModelAndView goLoginUser(){
		ModelAndView mv=new ModelAndView("/login");
		HttpSession session = ContextHolderUtils.getSession();
		User user= (User)session.getAttribute(Globals.USER_SESSION);
		if(user!=null){
			mv=new ModelAndView("redirect:/edu/index");
		/*	Subject subject = SecurityUtils.getSubject();
			if(subject.hasRole("student")){
	        	mv=new ModelAndView("redirect:/four/"+user.getUsername());
	        }else if(subject.hasRole("teacher")){
	        	mv=new ModelAndView("redirect:/main");
	        }else{
	        	mv=new ModelAndView("/unauthorized");
	        }*/
		}
		return mv;
	}
	/*
		@RequestMapping(method=RequestMethod.GET, value="/add")
	public ModelAndView addUser(){
		ModelAndView mv=new ModelAndView("/login");
		User user=new User();
		user.setId(1l);
		user.setUsername("admin");
		user.setPassword("1");
		user.setIstrue(1);
		try {
			userService.createUser(user);
		} catch (AddException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	} 
	
	@RequestMapping(method=RequestMethod.GET, value="/noPermi")
	public ModelAndView noPermi(){
		ModelAndView mv=new ModelAndView("/unauthorized");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="/changepwd/{old}/{new}",method=RequestMethod.POST)
	public ResultBean getBookBorrow(@PathVariable("old") String old,@PathVariable("new") String newpwd){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user =(User)session.getAttribute(Globals.USER_SESSION);
		if((!StringUtils.isEmpty(old))&&(!StringUtils.isEmpty(old))){
			if(userService.checkPassword(user.getId(), old)){
				userService.changePassword(user.getId(), newpwd);
				rb.setTrue(true);
				return rb;
			}
		}
		rb.setTrue(false);
		return rb;
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView getuserList(){
		ModelAndView mv=new ModelAndView("/system/permiss/user");
		return mv;
	}
	*/
	
	
	
}
