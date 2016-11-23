package com.jhkj.mosdc.permission.action;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.jhkj.mosdc.framework.action.BaseAction;
import com.jhkj.mosdc.framework.bean.ApplicationComponentStaticRetriever;
import com.jhkj.mosdc.framework.cas.CasThreadVariable;
import com.jhkj.mosdc.framework.cas.UrlVariable;
import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permiss.domain.User;
import com.jhkj.mosdc.permiss.service.UserService;
import com.jhkj.mosdc.permission.po.TsUser;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.wiscom.is.IdentityFactory;
import com.wiscom.is.IdentityManager;

public class OuterSystemLoginAction extends BaseAction {

	public String execute() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String is_config = request.getRealPath("/client.properties");
			Cookie all_cookies[] = request.getCookies();
			Cookie myCookie;
			String decodedCookieValue = null;
			if (all_cookies != null) {
				for (int i = 0; i < all_cookies.length; i++) {
					myCookie = all_cookies[i];
					if (myCookie.getName().equals("iPlanetDirectoryPro")) {
						decodedCookieValue = URLDecoder.decode(
								myCookie.getValue(), "GB2312");
					}
				}
			}
			if(decodedCookieValue==null){
				return "login";
			}
			IdentityFactory factory = IdentityFactory.createFactory(is_config);
			IdentityManager im = factory.getIdentityManager();
			String loginName = im.getCurrentUser(decodedCookieValue);
			
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
//				String page = request.getParameter("page");
//				String params = request.getParameter("params");
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
			
//			this.setUserInfo(curUser);
//			return "success";
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "login";
	}

	public void setUserInfo(String loginName) throws Throwable{
		BaseDao baseDao = (BaseDao)ApplicationComponentStaticRetriever.getComponentByItsName("baseDao");
		TsUser user = new TsUser();
		user.setLoginName(loginName);
		user=(TsUser)baseDao.loadLastEqual(user);
		HttpServletRequest request = ServletActionContext.getRequest();
		ActionContext ac = ServletActionContext.getContext();
		Method method;
		Object serviceBean = ApplicationComponentStaticRetriever.getComponentByItsName("userInfoService");
		Class cls = serviceBean.getClass();
		Class[] paraTypes = new Class[]{String.class};
		method = cls.getMethod("queryUser", paraTypes);		
		String paraValues = "{loginName:'"+loginName+"',password:'"+user.getPassword()+"',isOuterSystem:'true'}";
		UserInfo userInfo = (UserInfo) method.invoke(serviceBean, paraValues);
		Object baseBean = ApplicationComponentStaticRetriever.getComponentByItsName("baseService");
		Class baseCls = baseBean.getClass();
		Class[] baseParaTypes = new Class[]{String.class};
		method = baseCls.getMethod("getDefXnxq", baseParaTypes);
		String baseParaValues = "";
		Map map = (Map) method.invoke(baseBean, baseParaValues);
		if(map!=null){
			ac.getSession().put("xnId", map.get("xnId"));
			ac.getSession().put("xqId", map.get("xqId"));
			ac.getSession().put("xndm", map.get("xndm"));
			ac.getSession().put("xqdm", map.get("xqdm"));
		}
		ac.getSession().put(SysConstants.SESSION_USERINFO, userInfo);
		request.getSession().setMaxInactiveInterval(7200);//设置缓存失效时间
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
