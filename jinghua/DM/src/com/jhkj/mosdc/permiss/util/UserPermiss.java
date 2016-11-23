package com.jhkj.mosdc.permiss.util;

import org.apache.struts2.ServletActionContext;

import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permiss.domain.User;

public class UserPermiss {

	/**
	 * 获取当前登录的用户
	 * @return
	 */
	public static User getUser() {
		return (User) ServletActionContext.getRequest().getSession()
				.getAttribute(SysConstants.SESSION_USER);
	}
}
