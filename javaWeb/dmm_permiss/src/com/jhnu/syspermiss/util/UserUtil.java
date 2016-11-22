package com.jhnu.syspermiss.util;

import org.jasig.cas.client.authentication.AttributePrincipal;

public class UserUtil {
	
	public static String getCasLoginName(){
		//获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = null;  
		if(principal!=null){
			username = principal.getName();  
		}
	    return username;
	}
	
	public static byte[] getPhotoByUserName(String userName){
		String photoPath=PropertiesUtils.getBasePropertiesByName("sys.photoPath")+"/"+userName+".jpg";
		byte[] photo = FileUtil.readFileByBytes(photoPath);
		if(photo==null){
			photoPath=PropertiesUtils.getBasePropertiesByName("sys.photoPath")+"/"+
			PropertiesUtils.getBasePropertiesByName("sys.defaultPhoto");
			photo = FileUtil.readFileByBytes(photoPath);
		}
		return photo;
	}
	
}
