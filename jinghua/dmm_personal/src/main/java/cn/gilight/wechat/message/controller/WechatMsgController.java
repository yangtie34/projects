package cn.gilight.wechat.message.controller;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sword.wechat4j.message.CustomerMsg;
import org.sword.wechat4j.message.MassMessage;
import org.sword.wechat4j.user.User;
import org.sword.wechat4j.user.UserManager;

import com.alibaba.fastjson.JSONObject;

import cn.gilight.framework.uitl.HttpResult;

/**   
* @Description: TODO
* @author Sunwg  
* @date 2016年5月9日 下午6:14:39   
*/
@Controller("wechatMsgController")
@RequestMapping("/wechat/msg")
public class WechatMsgController {
	private Logger log = Logger.getLogger(WechatMsgController.class);
	
	@RequestMapping("/sendMassText")
	@ResponseBody
	public HttpResult sendMassText(String message){
		HttpResult result = new HttpResult();
		log.debug("群发文本");
		UserManager manager = new UserManager();
		List<String> users = manager.allSubscriber();
		MassMessage mass = new MassMessage();
		JSONObject msg = mass.sendText(users,message);
		result.setSuccess(true);
		result.setErrmsg(msg.getString("errmsg"));
		return result;
	}
	
	 
	/** 
	* @Description: 单个用户发送消息
	* @param message
	* @param toUserOpenId
	* @throws IOException void
	* @throws 
	*/
	@RequestMapping("/sendMessageToUser")
	@ResponseBody
	public void sendMessageToUser(String message,String toUser) throws IOException {
		// 主动发送客服消息
		CustomerMsg customerMsg = new CustomerMsg(toUser);
		customerMsg.sendText(message);
	}
		
	/** 
	* @Title: sendNews
	* @Description:发送新闻
	* @param request
	* @throws IOException
	*/
	@RequestMapping("/sendNew")
	@ResponseBody
	public boolean sendNew(String title,String content,String imageUrl,String url) throws IOException {
		// 主动发送客服消息
		// 获得关注者列表，发送给每个人
		UserManager userManager = new UserManager();
		List<String> userList = userManager.subscriberList().getData().getOpenid();
		for (int i = 0; i < userList.size(); i++) {
			String toUserOpenId = userList.get(i);
			User user = userManager.getUserInfo(toUserOpenId);
			CustomerMsg customerMsg = new CustomerMsg(toUserOpenId);
			customerMsg.sendNew(title,content,user.getHeadimgUrl(),url);
		}
		return true;
	}
}