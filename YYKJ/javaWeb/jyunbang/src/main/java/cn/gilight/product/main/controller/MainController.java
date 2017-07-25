package cn.gilight.product.main.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gilight.framework.uitl.common.UserUtil;
import cn.gilight.product.common.school.service.SchoolService;


@Controller
public class MainController {
	@Autowired 
	SchoolService schoolService;
	@RequestMapping(value="/main",method=RequestMethod.GET)
	public ModelAndView goMain(){
		ModelAndView mv=new ModelAndView("/main/main");
		String username=UserUtil.getCasLoginName();
		mv.addObject("username", username);
		return mv;
	}
	@RequestMapping(value="/MyJsp",method=RequestMethod.GET)
	public ModelAndView MyJsp(){
		ModelAndView mv=new ModelAndView("/main/MyJsp");
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
 