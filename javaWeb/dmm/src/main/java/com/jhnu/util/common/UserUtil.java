package com.jhnu.util.common;

import javax.servlet.http.HttpSession;

import com.jhnu.system.permiss.entity.User;
import com.jhnu.util.product.Globals;

public class UserUtil {
	public static User getLoginUser(){
		HttpSession session=ContextHolderUtils.getSession();
		return (User)session.getAttribute(Globals.USER_SESSION);
	}
}
