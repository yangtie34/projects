package com.jhnu.cas.login;

import java.lang.reflect.Method;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import com.jhnu.cas.entity.LoginResultBean;
import com.jhnu.util.common.ContextHolderUtils;
import com.jhnu.util.common.DateUtils;
import com.jhnu.util.common.HttpRequestDeviceUtils;
import com.jhnu.util.common.IpMacUtil;
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
			if(e.getCause()!=null&&e.getCause().getClass()==CannotGetJdbcConnectionException.class){
				resquest.setAttribute("errormsg", "数据库连接异常，请联系管理员");
				throw new FailedLoginException();
			}else if(e.getCause()!=null&&e.getCause().getCause()!=null&&e.getCause().getCause().getClass()==SQLException.class){
				resquest.setAttribute("errormsg", "数据库查询异常，请联系管理员");
				throw new FailedLoginException();
			}else{
				resquest.setAttribute("errormsg", "出现异常，请联系管理员");
				throw new FailedLoginException();
			}
		} 
    	if(!lrb.getIsTrue()){
			resquest.setAttribute("errormsg", lrb.getErrorMas());
			throw new FailedLoginException();
		}
    	credential.setUsername(lrb.getUsername());
    	
    	try{
    	final String SQL = "insert into T_SYS_USER_LOGIN_LOG values(?,?,?,?,?,?)";
        String id=UUID.randomUUID().toString().replace("-", "0");
        getJdbcTemplate().update(SQL, new Object[]{id,lrb.getUsername(),DateUtils.SSS.format(new Date()),
        autoType,HttpRequestDeviceUtils.getLoginType(resquest),IpMacUtil.getIp()});
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return createHandlerResult(credential, new SimplePrincipal(lrb.getUsername()), null);
    }
	
}
