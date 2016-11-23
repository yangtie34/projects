package com.jhkj.mosdc.permission.util;

import javax.servlet.http.HttpSession;

import com.jhkj.mosdc.framework.action.BaseAction;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permission.po.MenuTree;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.opensymphony.xwork2.ActionContext;


/**
 * @Comments: 用户信息和权限的公共方法
 * Company: 河南精华科技有限公司
 * Created by gaodongjie(gaodongjie@126.com) 
 * @DATE:2012-5-14
 * @TIME: 上午10:50:18
 */
public class UserPermissionUtil extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static MenuTree jxzzjgTree;
	private static MenuTree xzzzjgTree;
	private static MenuTree zzjgTree;
	private static UserInfo userInfo;
//	private static Map map =ActionContext.getContext().getSession();
	private static String xnId;
	private static String xqId;
	private UserPermissionUtil(){
		//定义存储用户信息和权限的数组
	}
	@Deprecated
	public static MenuTree getJxzzjgTree() {
		return jxzzjgTree;
	}
	@Deprecated
	public static MenuTree getXzzzjgTree() {
		return xzzzjgTree;
	}
	@Deprecated
	public static MenuTree getZzjgTree() {
		return zzjgTree;
	}

	public static void setJxzzjgTree(MenuTree jxzzjgTree) {
		UserPermissionUtil.jxzzjgTree = jxzzjgTree;
	}
	public static void setXzzzjgTree(MenuTree xzzzjgTree) {
		UserPermissionUtil.xzzzjgTree = xzzzjgTree;
	}
	public static void setZzjgTree(MenuTree zzjgTree) {
		UserPermissionUtil.zzjgTree = zzjgTree;
	}
	/**
     * 获取用户信息
     * @return
     * @throws Exception
     */
	@Deprecated
	public static UserInfo getUserInfo() {
		return (UserInfo) ActionContext.getContext().getSession().get(SysConstants.SESSION_USERINFO);
	}
	
	public static UserInfo setUserInfo(String userName) {
		
		return null;
	}
	
	public static UserInfo setUserInfo(String userName,String password) {
		
		return null;
	}
	
	
	/**
     * 获取session中的菜单按钮树
     * @return
     * @throws Exception
     */
	@Deprecated
	public static MenuTree getMenuPermiss() throws Exception{
    	//获取菜单按钮权限树
    	return (MenuTree) ActionContext.getContext().getSession().get(SysConstants.SESSION_PERMISSION);
    }
	@Deprecated
	public static MenuTree getJxzzjgPermiss() throws Exception{
    	//获取教学组织机构权限
    	return jxzzjgTree;
	}
	@Deprecated
	public static MenuTree getXzzzjgPermiss() throws Exception{
    	//获取行政组织机构权限
    	return xzzzjgTree;
	}
	@Deprecated
	public static MenuTree getZzjgPermiss() throws Exception{
		//获取行政组织机构权限
    	return zzjgTree;
	}
	@Deprecated
	public static String getXnId() {
		return ActionContext.getContext().getSession().get("xnId").toString();
	}
	@Deprecated
	public static String getXqId() {
		return ActionContext.getContext().getSession().get("xqId").toString();
	}
	
}
