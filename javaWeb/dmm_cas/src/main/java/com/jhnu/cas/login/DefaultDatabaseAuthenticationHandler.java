package com.jhnu.cas.login;

import java.lang.reflect.Method;
import java.security.GeneralSecurityException;

import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import com.jhnu.cas.entity.LoginResultBean;
import com.jhnu.util.common.ContextHolderUtils;
import com.jhnu.util.common.PropertiesUtils;

public class DefaultDatabaseAuthenticationHandler extends AbstractJdbcUsernamePasswordAuthenticationHandler {


	@Override
    protected HandlerResult authenticateUsernamePasswordInternal(UsernamePasswordCredential credential)
            throws GeneralSecurityException, PreventedException {
    	HttpServletRequest resquest=ContextHolderUtils.getRequest();
    	String autoType=resquest.getParameter("autoType");
    	final String username = credential.getUsername();
        final String password = credential.getPassword();
    	if(!StringUtils.hasText(autoType)){
    		autoType="default";
    	}
    	LoginResultBean lrb ;
    	try {
    		String classPath=PropertiesUtils.getProperties("/sso_me.properties", autoType);
			Class<?> c= Class.forName(classPath);
			Method m= c.getMethod("checkLogin",new Class[] { String.class,String.class,JdbcTemplate.class,HttpServletRequest.class });
			lrb =(LoginResultBean)m.invoke(c.getClass(),username, password,  getJdbcTemplate(), resquest);
		} catch (Exception e) {
			e.printStackTrace();
			resquest.setAttribute("errormsg", "出现异常，请联系管理员");
			throw new FailedLoginException();
		} 
    	if(!lrb.getIsTrue()){
			resquest.setAttribute("errormsg", lrb.getErrorMas());
			throw new FailedLoginException();
		}
    	credential.setUsername(lrb.getUsername());
    	return createHandlerResult(credential, new SimplePrincipal(lrb.getUsername()), null);
    }
	
}
