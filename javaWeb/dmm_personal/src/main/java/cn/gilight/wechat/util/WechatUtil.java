package cn.gilight.wechat.util;

import java.io.IOException;

import org.sword.lang.HttpUtils;
import org.sword.wechat4j.common.Config;
import org.sword.wechat4j.user.User;
import org.sword.wechat4j.user.UserManager;

import cn.gilight.personal.po.TWechatBind;

import com.alibaba.fastjson.JSONObject;

/**   
* @Description: 微信工具类，辅助开发  获取微信信息  或 生成一些微信的访问连接
* @author Sunwg  
* @date 2016年3月9日 下午4:10:14   
*/
public class WechatUtil {
	
	/** 
	* @Title: generateGetCodeUrl 
	* @Description: 传入redirectUrl返回获取用户openid用的code的访问连接
	* @param redirectUrl
	* @return String
	*/
	public static String generateGetCodeUrl(String redirectUrl){
		String appid = Config.instance().getAppid();
		String oauthurl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid
		+"&redirect_uri="+redirectUrl
		+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
		return oauthurl;
	}
	
	
	/** 
	* @Title: getOpenidAndAccesstoken 
	* @Description: TODO 根据code返回用户的openid和access_token
	* @param code
	* @throws IOException
	* @return JSONObject
	*/
	public static JSONObject getOpenidAndAccesstoken(String code) throws IOException{
		String appid = Config.instance().getAppid();
		String secret = Config.instance().getAppSecret();
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid
		+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
		String result = HttpUtils.get(url); 
		return JSONObject.parseObject(result);
	}
	
	/** 
	* @Title: fillWechatUserInfo 
	* @Description: 填充微信绑定用户的基本信息
	* @param user
	* @return TWechatBind
	*/
	public static TWechatBind fillWechatUserInfo(TWechatBind twb){
		UserManager manager = new UserManager();
		User user = manager.getUserInfo(twb.getOpenid());
		twb.setProvince(user.getProvince());
		twb.setCity(user.getCity());
		twb.setWechatName(user.getNickName());
		twb.setWechatSex(user.getSex());
		twb.setWechatHeadImg(user.getHeadimgUrl());
		return twb;
	}
	
}
