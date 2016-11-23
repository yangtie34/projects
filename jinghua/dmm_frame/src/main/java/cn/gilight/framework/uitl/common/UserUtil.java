package cn.gilight.framework.uitl.common;

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
	
}
