package com.jhnu.util.login;

import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.JdbcTemplate;

import com.jhnu.cas.entity.LoginResultBean;
import com.jhnu.cas.entity.User;
import com.wiscom.is.IdentityFactory;
import com.wiscom.is.IdentityManager;
/**
 * 金智老版本单点登录端口
 * @author Administrator
 *
 */
public class WiseduLoginUtil {
	
	public static LoginResultBean checkLogin(String username,String password,JdbcTemplate jdbcTemplate,HttpServletRequest resquest){
		LoginResultBean lrb = CommonLoginUtil.checkUserName(username,jdbcTemplate);
		if(!lrb.getIsTrue()){
			return lrb;
		}

		lrb=checkSSO(username,resquest);
    	if(!lrb.getIsTrue()){
    		return lrb;
		}

		lrb=CommonLoginUtil.checkIsTrue((User)lrb.getObject());
    	if(!lrb.getIsTrue()){
    		return lrb;
		}
		return lrb;
	}

	private static LoginResultBean checkSSO(String username,HttpServletRequest resquest){
		LoginResultBean lrb = new LoginResultBean();
		try {
			String is_config = resquest.getSession().getServletContext().getRealPath("/client.properties");
			Cookie all_cookies[] = resquest.getCookies();
			Cookie myCookie;
			String decodedCookieValue = null;
			if (all_cookies != null) {
				for (int i = 0; i < all_cookies.length; i++) {
					myCookie = all_cookies[i];
					if (myCookie.getName().equals("iPlanetDirectoryPro")) {
						decodedCookieValue = URLDecoder.decode(
								myCookie.getValue(), "GB2312");
					}
				}
			}
			if(decodedCookieValue==null){
				lrb.setTrue(false);
				lrb.setErrorMas("认证失败");
				return lrb;
			}
			IdentityFactory factory = IdentityFactory.createFactory(is_config);
			IdentityManager im = factory.getIdentityManager();
			String curUser = im.getCurrentUser(decodedCookieValue);
			if(!curUser.equals(username)){
				lrb.setTrue(false);
				lrb.setErrorMas("认证失败");
				return lrb;
			}
		}catch (Throwable e) {
			e.printStackTrace();
			lrb.setTrue(false);
			lrb.setErrorMas("认证失败");
			return lrb;
		}
		return lrb;
	}
	
}
