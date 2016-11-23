package cn.gilight.framework.uitl.common;

import java.util.Properties;

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
		Properties p=PropertiesUtils.getProperties("/permiss/sysConfig.properties");
		String photoPath=p.getProperty("sys.photoPath")+"/"+userName+".jpg";
		byte[] photo = FileUtil.readFileByBytes(photoPath);
		if(photo==null){
			photoPath=p.getProperty("sys.photoPath")+"/"+
					p.getProperty("sys.defaultPhoto");
			photo = FileUtil.readFileByBytes(photoPath);
		}
		return photo;
	}
	
}
