package cn.gilight.qyh.util;

import org.sword.lang.HttpUtils;

import cn.gilight.qyh.token.TokenProxy;

import com.alibaba.fastjson.JSONObject;

public class QyhUtil {
	public static String generateWechatCodeUrl(){
		String result = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=";
		result += QyhConfig.getProperty("qyh.CorpID");
		result += "&redirect_uri=";
		result += QyhConfig.getProperty("qyh.serverUrl");
		result += "/qyh/gerUserInfo&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
		return result;
	}
	
	public static JSONObject getUserInfo(String code){
		String accesstoken = TokenProxy.accessToken();
		String url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token="+accesstoken+"&code="+code;
		String resultStr = HttpUtils.get(url);
		JSONObject user = JSONObject.parseObject(resultStr);
		return user;
	}
}
