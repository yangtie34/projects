package com.jhnu.framework.util.common;

import javax.servlet.http.HttpSession;

import com.jhnu.edu.entity.User;
import com.jhnu.framework.util.product.Globals;


public class UserUtil {
	public static User getLoginUser(){
		HttpSession session=ContextHolderUtils.getSession();
		return (User)session.getAttribute(Globals.USER_SESSION);
	}
}
