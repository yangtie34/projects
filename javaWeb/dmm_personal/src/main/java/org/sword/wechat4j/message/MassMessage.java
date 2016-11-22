package org.sword.wechat4j.message;
import java.util.List;

import org.apache.log4j.Logger;
import org.sword.lang.HttpUtils;
import org.sword.wechat4j.token.TokenProxy;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
public class MassMessage {
	private static Logger logger = Logger.getLogger(CustomerMsg.class);
	
	private static final String MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=";
	
	public JSONObject sendText(List<String> toUsers,String msgBody){
		logger.debug("群发消息:" + msgBody); 
		if (toUsers.size() == 0) {
			return null;
		}
		JSONObject response = new JSONObject();
		JSONArray userlist = new JSONArray();
		for(String usr :  toUsers){
			userlist.add(usr);
		}
		response.put("touser", userlist);
		response.put("msgtype", "text");
		JSONObject msg = new JSONObject();
		msg.put("content", msgBody);
		response.put("text", msg);
		String rst = HttpUtils.post(MSG_URL + TokenProxy.accessToken(), response.toJSONString());
		JSONObject result = JSONObject.parseObject(rst);
		return result;
	}
}