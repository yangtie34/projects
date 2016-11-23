package cn.gilight.personal.student.bind.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.PasswordHelperUtil;
import cn.gilight.personal.po.TSysUser;
import cn.gilight.personal.po.TWechatBind;
import cn.gilight.wechat.util.WechatUtil;

@Controller("studentBindController")
@RequestMapping("/student/bind")
public class BindController {
	private Logger log = Logger.getLogger(BindController.class);
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernate;
	
	@RequestMapping("/wechat")
	@ResponseBody
	@Transactional
	public Map<String, Object> bindWechat(String openid,String username,String password) {
		TWechatBind user = new TWechatBind();
		user.setUsername(username);
		user.setOpenid(openid);
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean success = false;
		TSysUser sysuser = new TSysUser()                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           ;
		sysuser.setUsername(username);
		List<TSysUser> suls = hibernate.findByExample(sysuser);
		if(suls.size() > 0){
			TSysUser sus = suls.get(0);
			if(sus.getPassword().equals(PasswordHelperUtil.simpleEncryptPassword(username + sus.getSalt(), password))){
				//删除已经绑定的用户，防止多个学生的微信绑定到同一个学生信息
				TWechatBind temp = new TWechatBind();
				temp.setOpenid(openid);
				List<TWechatBind> bindedUser = hibernate.findByExample(temp);
				for (int i = 0; i < bindedUser.size(); i++) {
					hibernate.delete(bindedUser.get(i));
				}
				
				/*开始执行绑定操作将用户和微信的openID进行绑定，下次登录的时候直接进入系统而不需要认证*/
				user = WechatUtil.fillWechatUserInfo(user);
				user.setSf(2);
				user.setXsId(username);
				try {
					hibernate.save(user);
					log.debug("++学生["+user.getUsername()+"]登录微信绑定了个人服务++" );
					success = true;
				} catch (Exception e) {
					e.printStackTrace();
					result.put("eroormsg", "系统错误，绑定失败，请重试！");
				}  
				
			}else{
				result.put("eroormsg", "用户名或者密码错误！");
				log.debug("++学生["+user.getUsername()+"]绑定微信号失败，用户名或密码错误++" );
			}
		}else{
			result.put("eroormsg", "用户名或者密码错误！");
			log.debug("++学生["+user.getUsername()+"]绑定微信号失败，系统中未找该用户++" );
		}
		result.put("success", success);
		return result;
	}
}