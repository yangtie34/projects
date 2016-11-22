package com.jhnu.util.login;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.JdbcTemplate;

import com.jhnu.cas.entity.LoginResultBean;
import com.jhnu.cas.entity.User;
import com.jhnu.util.catche.MacForPassCache;
import com.jhnu.util.common.BidCreate;
import com.jhnu.util.common.IpMacUtil;

/**
 * 公司数字化校园
 * @author Administrator
 *
 */
public class MosdcLoginUtil {
	
	public static LoginResultBean checkLogin(String username,String password,JdbcTemplate jdbcTemplate,HttpServletRequest resquest){
		
		LoginResultBean lrb = CommonLoginUtil.checkUserName(username,jdbcTemplate);
		if(!lrb.getIsTrue()){
			return lrb;
		}
		
		User user=(User)lrb.getObject();
		lrb=checkPassword(user,password);
		if(!lrb.getIsTrue()){
			return lrb;
		}
		
    	lrb=CommonLoginUtil.checkIsTrue(user);
    	if(!lrb.getIsTrue()){
    		return lrb;
		}
		
		return lrb;
	}
	
	private static LoginResultBean checkPassword(User user,String password){
		String errormsg="";
		String username=user.getUsername();
		LoginResultBean jrb=new LoginResultBean();
		String ip=IpMacUtil.getIp();
		if(MacForPassCache.isFreeze(ip, username)){
			Map<String,Long> usermap=MacForPassCache.getFreezeInfo(ip, username);
			errormsg="暂处于冻结状态！请于"+usermap.get("secs")+"分钟后再次登陆！如忘记密码，请联系管理员";
			jrb.setTrue(false);
		}else if (!user.getPassword().equals(getMosdcPassword(password))) {
	    	MacForPassCache.setFreeze(ip, username);
			Map<String,Long> usermap=MacForPassCache.getFreezeInfo(ip, username);
			errormsg="密码错误！错误次数"+usermap.get("cs")+",请核对后修改！";
			jrb.setTrue(false);
    	}else{
			MacForPassCache.moveFreeze(ip, username);
		}
		jrb.setErrorMas(errormsg);
		return jrb;
	}
	
	 private static String getMosdcPassword(String password){
		    return BidCreate.Encrypt(password).toUpperCase();
	 }

}
