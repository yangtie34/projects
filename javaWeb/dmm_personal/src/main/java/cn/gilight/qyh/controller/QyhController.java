package cn.gilight.qyh.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gilight.qyh.contact.util.ContactUtil;
import cn.gilight.qyh.po.QyhBind;
import cn.gilight.qyh.util.HttpResult;
import cn.gilight.qyh.util.QyhUtil;

import com.alibaba.fastjson.JSONObject;

/**   
* @Description: TODO 微信消息解析案例 实现
* @author Sunwg  
* @date 2016年1月14日 下午5:18:36   
*/
@Controller("Controller")
@RequestMapping("/qyh")
public class QyhController {
	private Logger log = Logger.getLogger(this.getClass());
	@RequestMapping("redirect")
	public void redirect(HttpServletResponse response) throws IOException {
		response.sendRedirect(QyhUtil.generateWechatCodeUrl());
	}
	
	@RequestMapping("gerUserInfo")
	public ModelAndView getUserInfo(String code) throws IOException {
		ModelAndView mv = new ModelAndView();
		JSONObject userInfo = QyhUtil.getUserInfo(code);
		if(userInfo.getString("UserId") != null && !userInfo.getString("UserId").isEmpty()){
			mv.setViewName("/qyh/qyhewm");
		}else{
			mv.setViewName("/qyh/bind/bind");
			mv.addObject("openid",userInfo.getString("OpenId"));
		}
		return mv;
	}
	
	@RequestMapping("bindInfo")
	@ResponseBody
	public HttpResult bind(QyhBind user) throws IOException {
		HttpResult result = new HttpResult();
		log.debug("用户提交信息到微信通讯录");
		
		/**
		 * 企业号绑定部分流程已经走通
		 * 尚待完善内容 ： 
		 *  1.建立本地绑定表，插入绑定用户信息
		 *  2.校验用户信息是否已经在数据库中存在
		 *  3.根据校验信息返回对应的处理结果
		 *  4.修改提交用户信息的内容
		 */
		
		result = ContactUtil.addUser(user);
		if(result.getErrcode() == 0){
			result.setSuccess(true);
		}else{
			result.setSuccess(false);
		}
		return result;
	}
}