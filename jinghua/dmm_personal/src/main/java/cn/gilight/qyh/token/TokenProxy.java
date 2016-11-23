/**
 * 
 */
package cn.gilight.qyh.token;

import java.util.Timer;

import org.apache.log4j.Logger;

import cn.gilight.qyh.token.timer.AccessTokenTimer;

/**   
* @Description: 获取企业号的accessToken
* @author Sunwg  
* @date 2016年4月14日 下午5:52:30   
*/
public class TokenProxy {
	private static Logger log = Logger.getLogger(TokenProxy.class);
	/**
	 * 通过代理得到accessToken的串
	 */
	public static String accessToken(){
		String result = AccessToken.ACCESS_TOKEN;
		if(result==null || result.isEmpty()){
			AccessToken accessToken = new AccessToken();
			//获取成功之后持久化accessToken
			accessToken.request();
			Timer timer = new Timer(true);
			log.info("企业号accessToken监听器启动..........");
			AccessTokenTimer accessTokenTimer = new AccessTokenTimer();
			timer.schedule(accessTokenTimer, AccessTokenTimer.DELAY,AccessTokenTimer.PERIOD);
			log.info("accessToken定时器注册成功，执行间隔为" + AccessTokenTimer.PERIOD);
		}
		return AccessToken.ACCESS_TOKEN;
	}
	
}
