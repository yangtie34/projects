package cn.gilight.wechat.user.service.impl;

import java.util.List;

import org.sword.wechat4j.user.User;

import cn.gilight.personal.po.TWechatBind;

/**   
* @Description: 微信用户信息获取service
* @author Sunwg  
* @date 2016年5月11日 上午11:19:07   
*/
public interface WechatUserService {

	/** 
	 * @Description: 获取 微信关注者信息
	 * @return List<User>
	 */
	public abstract List<User> getSubcribeUserInfo();

	/** 
	 * @Description: 获取和系统绑定的微信用户信息
	 * @return List<TWechatBind>
	 */
	public abstract List<TWechatBind> getBindUserInfo();

	/** 
	 * @Description: 根据系统用户名获取和系统绑定的微信用户信息
	 * @return TWechatBind
	 */
	public abstract TWechatBind getBindUserInfoByUsername(String username);

}