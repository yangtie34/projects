package com.jhkj.mosdc.permiss.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;

import com.jhkj.mosdc.framework.bean.ApplicationComponentStaticRetriever;
import com.jhkj.mosdc.framework.cas.CasThreadVariable;
import com.jhkj.mosdc.framework.cas.UrlVariable;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permiss.domain.User;
import com.jhkj.mosdc.permiss.service.UserService;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CasLoginAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(LoginAction.class);
	public String loginName;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String execute() {
		// long l = System.currentTimeMillis();
		HttpServletRequest request = ServletActionContext.getRequest();
		Assertion assertion = (Assertion) request
				.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
		if (assertion != null) {
			loginName = assertion.getPrincipal().getName();
		} else {
			return SUCCESS;
		}

		UserService userservice = (UserService) ApplicationComponentStaticRetriever.getComponentByItsName("userManager");
		boolean login = userservice.queryUserExsit(loginName, null);
		if (login) {
			User user = (User) ApplicationComponentStaticRetriever
					.getComponentByItsName("userDomain");
			user.initUser(loginName);// 初始化用户权限
			user.initProxyUser();// 初始化代理用户权限
			ActionContext ac = ServletActionContext.getContext();
			//获取单点登录要打开的页面
			UrlVariable uv = CasThreadVariable.getThreadVariable(Thread.currentThread().getId());
//			String page = request.getParameter("page");
//			String params = request.getParameter("params");
			String page = uv.getPage();
			String params = uv.getParams();
			CasThreadVariable.remove(Thread.currentThread().getId());
			//设置页面加载后要打开的页面
			request.getSession().setAttribute("openPage", page);
			request.getSession().setAttribute("openPageParams", params);

			// 设置Session
			request.getSession().setAttribute(SysConstants.SESSION_USER, user);
			request.getSession().setAttribute(SysConstants.SESSION_USERINFO,
					user.translateToUserInfo());
			request.getSession()
					.setMaxInactiveInterval(60 * 60 * 4);// 失效时间
			return Action.SUCCESS;
		} else {
			return LOGIN;
		}

	}
}
