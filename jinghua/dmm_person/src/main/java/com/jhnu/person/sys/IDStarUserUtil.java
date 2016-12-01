package com.jhnu.person.sys;

import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.client.util.AssertionHolder;

import com.jhnu.syspermiss.GetCachePermiss;
import com.jhnu.syspermiss.permiss.entity.User;
import com.jhnu.syspermiss.util.ContextHolderUtils;
import com.jhnu.syspermiss.util.FileUtil;
import com.jhnu.syspermiss.util.PropertiesUtils;

public class IDStarUserUtil {
	public static String getUserName(){
		String username=AssertionHolder.getAssertion().getPrincipal().getName();
		return username;
	}
	
	public static void initPermiss(String userName){
	  	final HttpServletRequest Httprequest = (HttpServletRequest) ContextHolderUtils.getRequest();
		String path = Httprequest.getContextPath(); 
           String basePath = Httprequest.getScheme()+"://"+Httprequest.getServerName()+":"+Httprequest.getServerPort()+path;
           GetCachePermiss.init(userName,basePath);
	}
	
	public static String getPhotoByUserName(String userName){
		String photoPath=PropertiesUtils.getBasePropertiesByName("sys.photoPath")+"/"+userName+".jpg";
		byte[] photo = FileUtil.readFileByBytes(photoPath);
		if(photo==null){
			final HttpServletRequest Httprequest = (HttpServletRequest) ContextHolderUtils.getRequest();
			String path = Httprequest.getContextPath(); 
	           String basePath = Httprequest.getScheme()+"://"+Httprequest.getServerName()+":"+Httprequest.getServerPort()+path;
	           User user=GetCachePermiss.findByUsername(userName);
	           photoPath=basePath+"/static/resource/person/images/user-"+(user.getSalt().equalsIgnoreCase("男")?"boy":"girl")+".jpg";
			photo = FileUtil.readFileByBytes(photoPath);
		}
		return photoPath;
	}
}
