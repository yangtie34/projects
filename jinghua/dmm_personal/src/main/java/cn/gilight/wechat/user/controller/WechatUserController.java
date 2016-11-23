package cn.gilight.wechat.user.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sword.wechat4j.user.User;

import cn.gilight.personal.po.TWechatBind;
import cn.gilight.wechat.user.service.impl.WechatUserService;

/**   
* @Description: TODO
* @author Sunwg  
* @date 2016年5月9日 下午6:14:39   
*/
@Controller("wechatUserController")
@RequestMapping("/wechat/user")
public class WechatUserController {
	private Logger log = Logger.getLogger(WechatUserController.class);
	
	@Resource
	private WechatUserService userService;
	
	@RequestMapping("/getSubscriberUserInfo")
	@ResponseBody
	public List<User> getSubscriberUsersInfo() throws IOException {
		log.debug("获取关注微信用户");
		List<User> result = userService.getSubcribeUserInfo();
		return result;
	}
	
	@RequestMapping("/getBindUserInfo")
	@ResponseBody
	public List<TWechatBind> getBindUserInfo() throws IOException {
		log.debug("获取系统绑定微信用户");
		List<TWechatBind> result = userService.getBindUserInfo();
		return result;
	}
	
	@RequestMapping("/getUserInfoByUsername")
	@ResponseBody
	public TWechatBind getBindUsersInfoByUsername(String username) throws IOException {
		TWechatBind result = userService.getBindUserInfoByUsername(username);
		return result;
	}
}