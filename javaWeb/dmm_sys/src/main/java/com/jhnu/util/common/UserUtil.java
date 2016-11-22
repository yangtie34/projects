package com.jhnu.util.common;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.jhnu.system.permiss.entity.User;
import com.jhnu.util.product.Globals;

public class UserUtil {
	public static User getLoginUser(){
		HttpSession session=ContextHolderUtils.getSession();
		return (User)session.getAttribute(Globals.USER_SESSION);
	}
	
	public static String getCasLoginName(){
		Subject subject = SecurityUtils.getSubject();  
	    return (String)subject.getPrincipal();
	}
	
	public static byte[] getPhotoByUserName(String userName){
		String photoPath=PropertiesUtils.getDBPropertiesByName("sys.photoPath")+"/"+userName+".jpg";
		byte[] photo = FileUtil.readFileByBytes(photoPath);
		if(photo==null){
			photoPath=PropertiesUtils.getDBPropertiesByName("sys.photoPath")+"/"+
			PropertiesUtils.getDBPropertiesByName("sys.defaultPhoto");
			photo = FileUtil.readFileByBytes(photoPath);
		}
		return photo;
	}
}
