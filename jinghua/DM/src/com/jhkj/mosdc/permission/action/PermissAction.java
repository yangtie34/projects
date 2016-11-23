package com.jhkj.mosdc.permission.action;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.jhkj.mosdc.framework.action.BaseAction;
import com.jhkj.mosdc.framework.bean.ApplicationComponentStaticRetriever;
import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.service.DeskTopService;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permission.po.MenuTree;
import com.jhkj.mosdc.permission.po.TsUser;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.jhkj.mosdc.permission.util.UserPermissionUtil;
import com.opensymphony.xwork2.ActionContext;


public class PermissAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(PermissAction.class);
	
	public String execute() {
		MenuTree menuTree = null; 
		UserInfo userInfo = null;
		Map<String,MenuTree> dataPermissMap = null;
		ActionContext ac = ServletActionContext.getContext();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("Content-Type","text/html;charset=UTF-8");//设置http 头编码信息
		response.setCharacterEncoding("UTF-8");
		try {
			userInfo = UserPermissionUtil.getUserInfo();
			
			Method method,method1;
			Object permissBean = ApplicationComponentStaticRetriever.getComponentByItsName("permissService");
			Class permissCls = permissBean.getClass();
			Class[] paraTypes = new Class[]{String.class};
			method = permissCls.getMethod("queryPermissCdzy", paraTypes);
			menuTree = (MenuTree) method.invoke(permissBean, "");
			method1 = permissCls.getMethod("queryDataPermiss", paraTypes);
			if(userInfo!=null){
				dataPermissMap = (Map<String,MenuTree>) method1.invoke(permissBean, userInfo.getId().toString());
			}else{
				System.out.println(this.getClass()+"UserInfo is null. 首次登陆或登录超时导致用户信息为空。");
			}
			
			if(menuTree != null  && dataPermissMap != null){ 
				UserPermissionUtil.setJxzzjgTree(dataPermissMap.get("jxzzjgTree"));
				UserPermissionUtil.setXzzzjgTree(dataPermissMap.get("xzzzjgTree"));
				UserPermissionUtil.setZzjgTree(dataPermissMap.get("zzjgTree"));
				ac.getSession().put(SysConstants.SESSION_PERMISSION, menuTree);
			}else {
				return LOGIN;
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return LOGIN;
		}
		String loginPage = null;
//		try {
//			DeskTopService service = (DeskTopService) ApplicationComponentStaticRetriever.getComponentByItsName("desktopservice");
//			Map  systemstyle = service.queryUserSystemPage("");
//			loginPage = (String) systemstyle.get("login_page");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return LOGIN;
//		}
//		if(userInfo == null){
//			return LOGIN;
//		}
		loginPage = "testPage.jsp";
		return loginPage;
//		return SUCCESS;
	}
	
	
	
}
