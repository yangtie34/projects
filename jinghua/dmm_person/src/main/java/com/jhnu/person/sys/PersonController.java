package com.jhnu.person.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.person.stu.service.StuInfoService;
import com.jhnu.person.tea.service.TeaInfoService;
import com.jhnu.syspermiss.GetCachePermiss;
import com.jhnu.syspermiss.catche.PermissCache;
import com.jhnu.syspermiss.util.UserUtil;
import com.jhnu.util.common.ContextHolderUtils;


@Controller
@RequestMapping("/person")
public class PersonController {	
	@Autowired
	private BaseDao baseDao;
	@Autowired
	private StuInfoService stuInfoService;
	@Autowired
	private TeaInfoService teaInfoService;
	
	@RequestMapping(method=RequestMethod.GET, value="index")
	public ModelAndView goToIndex(){
		String url="";
		String userName = UserUtil.getCasLoginName();
		String rootRole=GetCachePermiss.getUserRootRole(userName);
		String sql="";
		String sex=null;
		switch(rootRole){
		case "student":
			url="redirect:/stu/StuQj";
			if(PersonInfo.getOther(userName)==null){
				PersonInfo.setOUMap(userName, teaInfoService.OtherUser(userName));
			}
			sex=teaInfoService.getUserSex(userName);
			break;
		case "teacher":
			url="redirect:/tea/TeaQj";
			if(PersonInfo.getOther(userName)==null){
				PersonInfo.setOUMap(userName, stuInfoService.OtherUser(userName));
			}
			sex=teaInfoService.getUserSex(userName);
			break;
	/*	case "admin":
			url="/person/error";
			break;*/
		default:
			url="/person/error";
			break;
		}
		PermissCache.getCatcheEntityByName(userName).getUser().setSalt(sex);	
		ModelAndView mv=new ModelAndView(url);
		return mv;
	}
	@RequestMapping(method=RequestMethod.GET, value="error")
	public ModelAndView goToError(){
		ModelAndView mv=new ModelAndView("person/error");
		return mv;
	}
	@RequestMapping(method=RequestMethod.GET, value="other/{id}")///roleid/{id}")
	public ModelAndView stuInfo1(@PathVariable("id") String id){
		//教师
		String url="redirect:";
		if(teaInfoService.isTeacher(id)){
			url+="/tea/";
		}else{
			url+="/stu/";
		}
		url+=id;
		ModelAndView mv=new ModelAndView(url);
		return mv;
	}
}
