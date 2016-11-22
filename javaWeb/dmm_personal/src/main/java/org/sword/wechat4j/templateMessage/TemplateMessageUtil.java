package org.sword.wechat4j.templateMessage;

import org.sword.lang.HttpUtils;
import org.sword.wechat4j.token.TokenProxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class TemplateMessageUtil {
	public static final String GET_TEMPLATE_LIST_URL = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=";
	
	public static final String DELETE_TEMPLATE_URL = "https://api,weixin.qq.com/cgi-bin/template/del_private_template?access_token=";
	
	public static final String SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	
	public static final String GET_TEMPLATE_ID_URL = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=";

	
	public static JSONObject sendTemplateMessage(TemplateMessage message){
		String url = TemplateMessageUtil.SEND_TEMPLATE_URL + TokenProxy.accessToken();
		String result = HttpUtils.post(url, JSON.toJSONString(message));
		return JSONObject.parseObject(result);
	}
}