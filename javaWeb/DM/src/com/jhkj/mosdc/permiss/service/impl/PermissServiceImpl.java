package com.jhkj.mosdc.permiss.service.impl;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;

import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permiss.domain.Node;
import com.jhkj.mosdc.permiss.domain.User;
import com.jhkj.mosdc.permiss.service.PermissService;
import com.jhkj.mosdc.permiss.util.UserPermiss;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.opensymphony.xwork2.ActionContext;

public class PermissServiceImpl implements PermissService {
	/**
	 * 查询当前登录用户的菜单权限
	 */
	public String queryUserMenuTree(String params){
		ActionContext context = ServletActionContext.getContext();
		User user = (User) context.getSession().get(SysConstants.SESSION_USER);
		return Struts2Utils.bean2json(user.getUserMenuTree());
	}
	/**
	 * 查询当前登录用户
	 */
	public String queryUser(String params) throws Exception{
		return Struts2Utils.bean2json(UserPermiss.getUser().translateToUserInfo());
	}
	/**
	 * 查询教学组织-数据权限-树结构
	 */
	public String queryJxzzjgDataPermissionTree(String params) throws Exception{
		return Struts2Utils.bean2json(UserPermiss.getUser().getCurrentJxzzjgDataPermissTree());
	}
}
