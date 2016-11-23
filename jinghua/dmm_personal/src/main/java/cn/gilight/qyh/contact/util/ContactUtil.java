package cn.gilight.qyh.contact.util;

import org.sword.lang.HttpUtils;

import com.alibaba.fastjson.JSONObject;

import cn.gilight.qyh.po.QyhBind;
import cn.gilight.qyh.token.TokenProxy;
import cn.gilight.qyh.util.HttpResult;

public class ContactUtil {
	private static String saveUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=" + TokenProxy.accessToken();
	
	public static HttpResult addUser(QyhBind user){
		JSONObject json = new JSONObject();
		json.put("userid", user.getUsername());
		json.put("name","孙伟光" );
		json.put("department", 1 );
		json.put("mobile",user.getMobile());
		json.put("gender",1 );
		JSONObject httpJosn = JSONObject.parseObject(HttpUtils.post(saveUrl,json.toJSONString()));
		HttpResult result = JSONObject.toJavaObject(httpJosn, HttpResult.class);
		return result;
	}
}
