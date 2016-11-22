package cn.gilight.wechat.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sword.wechat4j.user.User;
import org.sword.wechat4j.user.UserManager;

import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.personal.po.TWechatBind;

@Service("wechatUserService")
public class WechatUserServiceImpl implements WechatUserService {
	
	@Resource
	private HibernateDao hibernate;
	
	@Override
	public List<User> getSubcribeUserInfo(){
		List<User> result = new ArrayList<User>();
		UserManager userManager = new UserManager();
		List<String> userList = userManager.subscriberList().getData().getOpenid();
		for (int i = 0; i < userList.size(); i++) {
			String toUserOpenId = userList.get(i);
			User user = userManager.getUserInfo(toUserOpenId);
			result.add(user);
		}
		return result;
	}
	
	@Override
	@Transactional
	public List<TWechatBind> getBindUserInfo(){
		TWechatBind temp = new TWechatBind();
		List<TWechatBind> result = hibernate.findByExample(temp);
		return result;
	}
	
	@Override
	@Transactional
	public TWechatBind getBindUserInfoByUsername(String username){
		TWechatBind temp = new TWechatBind();
		temp.setUsername(username);
		List<TWechatBind> result = hibernate.findByExample(temp);
		if(result.size() > 0){
			return result.get(0);
		}else return null;
	}
}
