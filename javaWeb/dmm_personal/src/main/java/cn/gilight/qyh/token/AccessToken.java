/**
 * 
 */
package cn.gilight.qyh.token;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.sword.lang.HttpUtils;

import cn.gilight.qyh.util.QyhConfig;

import com.alibaba.fastjson.JSONObject;



/**
 * Access token实体模型
 * @author ChengNing
 * @date   2014年12月12日
 */
public class AccessToken extends Token {
	
	private static Logger logger = Logger.getLogger(AccessToken.class);
	private static final String ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
	
	public static String ACCESS_TOKEN = null;
	
	@Override
	protected String tokenName() {
		return "access_token";
	}

	@Override
	protected String expiresInName() {
		return "expires_in";
	}
	
	public boolean request(){
		String url = accessTokenUrl();
		String result = HttpUtils.get(url);
		if(StringUtils.isBlank(result)){
			ACCESS_TOKEN =null;
			return false;
		}
		logger.info("token获取成功");
		JSONObject jsonObject = JSONObject.parseObject(result);
		String tokenName = tokenName();
		ACCESS_TOKEN = jsonObject.get(tokenName).toString();
		return true;
	}
	/**
	 * 组织accesstoken的请求utl
	 */
	@Override
	protected String accessTokenUrl() {
		String corpid = QyhConfig.getProperty("qyh.CorpID");
		String secrect = QyhConfig.getProperty("qyh.Secret");
		String url = ACCESS_TOKEN_URL + "?corpid="+corpid+"&corpsecret="+secrect;
		logger.info("创建获取access_token url");
		return url;
	}
	
	
}
