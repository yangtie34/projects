package cn.gilight.personal.teacher.main.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhnu.syspermiss.util.ContextHolderUtils;

import cn.gilight.framework.uitl.HttpResult;
import cn.gilight.framework.uitl.SysConfig;
import cn.gilight.personal.teacher.main.service.TeacherInfoService;

/**   
* @Description:微信消息解析案例 实现
* @author Sunwg  
* @date 2016年1月14日 下午5:18:36   
*/
@Controller("teacherMainController")
@RequestMapping("/teacher")
public class TeacherMainController {
	private Logger log = Logger.getLogger(TeacherMainController.class);
	
	@Resource
	private TeacherInfoService teacherSevice;
	
	@RequestMapping("/main")
	@ResponseBody
	public List<Map<String, Object>> home(HttpServletRequest request,String message) {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		log.debug("get user permissions");
		
		return result;
	}
	
	@RequestMapping("/getSelfInfo")
	@ResponseBody
	public Map<String, Object> getSelfInfo() {
		log.debug("this is the job running");
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		return teacherSevice.getTeacherSimpleInfo(username);
	}
	
	@RequestMapping("/getSelfInfoDetial")
	@ResponseBody
	public Map<String, Object> getSelfInfoDetial(){
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		//详细信息
		Map<String,Object> map = teacherSevice.getTeacherDetailInfo(username);
		String dmmUrl = SysConfig.instance().getDmmUrl();
		map.put("dmmUrl", dmmUrl);
		return map;
	}
	
	@RequestMapping("/submitAdvice")
	@ResponseBody
	public HttpResult submitAdvice(String advice){
		HttpResult result = new HttpResult();
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		try {
			teacherSevice.submitAdvice(username,advice);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/getSelfHistoryList")
	@ResponseBody
	public List<Map<String, Object>> getSelfHistoryList(){
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		return teacherSevice.queryTeacherHistoryList(username);
	}
	
	@RequestMapping("/getSelfHistoryInfo")
	@ResponseBody
	public  Map<String, Object> getSelfHistoryInfo(){
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		return teacherSevice.queryTeacherHistoryInfo(username);
	}
}
/*
	String dmmUrl = SysConfig.instance().getDmmUrl();
	
	//获取 登陆者所拥有的菜单
	List<Map<String,Object>> menus=(List<Map<String,Object>>)SystemHttpUtil.rpcPost(dmmUrl,"resourcesService","getMenusByUserName",list); 
	
	//获取 登陆者所有拥有的权限
	List<String> perm=(List<String>)SystemHttpUtil.rpcPost(dmmUrl,"resourcesService","getAllPermssionByUserName",list); 
	
	//验证 登陆者是否有该权限
	String checkShiro="qx:zy:view";
	list.add(checkShiro);
	boolean hasPerm=(Boolean)SystemHttpUtil.rpcPost(dmmUrl,"resourcesService","hasPermssion",list); 
	
	//获取 登陆者所拥有该权限中的数据权限的SQL
	String dataSql=(String)SystemHttpUtil.rpcPost(dmmUrl,"dataServeService","getDataServeSqlbyUserIdShrio",list); 
	
	//验证 登陆者是否具有该人员在该资源的数据权限
	list=new ArrayList<Object>();
	String chekcUser="201315040003";
	list.add(chekcUser);
	list.add(username);
	list.add(checkShiro);
	boolean hasData=(Boolean)SystemHttpUtil.rpcPost(dmmUrl,"dataServeService","hasThisOnePerm",list);
	
	System.out.println("登陆者为："+username);
	System.out.println("拥有的菜单："+menus);
	System.out.println("拥有的所有权限代码："+perm);
	System.out.println("验证是否有'"+checkShiro+"'权限："+hasPerm);
	System.out.println("登陆者对'"+checkShiro+"'权限的数据范围SQL："+dataSql);
	System.out.println("登陆者所拥有该权限中的数据权限的SQL:"+dataSql);
	System.out.println("登陆者是否具有人员'"+chekcUser+"'在'"+checkShiro+"'的数据权限："+hasData);
*/