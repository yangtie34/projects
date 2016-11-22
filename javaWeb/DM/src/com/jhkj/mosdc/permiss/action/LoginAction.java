package com.jhkj.mosdc.permiss.action;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.jhkj.mosdc.framework.action.BaseAction;
import com.jhkj.mosdc.framework.bean.ApplicationComponentStaticRetriever;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.output.dao.IPersonalInfoDao;
import com.jhkj.mosdc.permiss.domain.User;
import com.jhkj.mosdc.permiss.domain.UserPermission;
import com.jhkj.mosdc.permiss.service.UserService;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

/**
 * 登录Action
 * 
 * @author Administrator
 * 
 */
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(LoginAction.class);
	public String loginName;
	public String password;
	public String imageCode;

	public String login(){
		HttpServletRequest request = ServletActionContext.getRequest();
		UserService userservice = (UserService) ApplicationComponentStaticRetriever.getComponentByItsName("userManager");
		boolean login = userservice.queryUserExsit(loginName, password);
		if(login){
		   User user =  (User) ApplicationComponentStaticRetriever.getComponentByItsName("userDomain");
		   user.initUser(loginName);//初始化用户权限
		   user.initProxyUser();//初始化代理用户权限
		   ActionContext ac = ServletActionContext.getContext();
		   
		   //设置Session
		   ServletActionContext.getRequest().getSession().setAttribute(SysConstants.SESSION_USER,user);
		   ServletActionContext.getRequest().getSession().setAttribute(SysConstants.SESSION_USERINFO, user.translateToUserInfo());
		   ServletActionContext.getRequest().getSession().setMaxInactiveInterval(60*60*4);//失效时间
		   String loginIP = getIpAddr(request);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ahh:mm:ss");
			String loginTime = sdf.format(new Date());
			ac.getSession().put("loginIP", loginIP);
			ac.getSession().put("loginTime", loginTime);
			IPersonalInfoDao idao = (IPersonalInfoDao)ApplicationComponentStaticRetriever.getComponentByItsName("personalDao");
			idao.savePersonInfo(loginTime,loginIP);
		   return Action.SUCCESS;
		}else{
		   return LOGIN; 
		}
	}
	public String index(){
//		System.out.println(ServletActionContext.getRequest().getParameter("loginName"));
		return Action.SUCCESS;
	}
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImageCode() {
		return imageCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}
	
	/**
	 * 获取客户端ip的方法。
	 * @param request
	 * @return
	 */
	private String getIpAddr(HttpServletRequest request) {
	       String ip = request.getHeader("x-forwarded-for");
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("WL-Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getRemoteAddr();
	       }
	       return ip;
	   } 
}
