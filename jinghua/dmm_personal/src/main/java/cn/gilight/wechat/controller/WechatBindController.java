package cn.gilight.wechat.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.sword.wechat4j.event.EventType;
import org.sword.wechat4j.exception.WeChatException;
import org.sword.wechat4j.menu.Menu;
import org.sword.wechat4j.menu.MenuButton;
import org.sword.wechat4j.menu.MenuManager;
import org.sword.wechat4j.templateMessage.TemplateMessage;
import org.sword.wechat4j.templateMessage.TemplateMessageUtil;

import com.alibaba.fastjson.JSONObject;
import com.jhnu.syspermiss.util.ContextHolderUtils;
import com.jhnu.syspermiss.util.SysConfig;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.HttpResult;
import cn.gilight.framework.uitl.PasswordHelperUtil;
import cn.gilight.personal.po.TSysUser;
import cn.gilight.personal.po.TWechatBind;
import cn.gilight.wechat.util.WechatUtil;

/**   
* @Description: 微信消息解析案例 实现
* @author Sunwg  
* @date 2016年3月9日 下午12:28:17   
*/
@Controller("wechatBindController")
@RequestMapping("/wechat")
public class WechatBindController {
	private Logger log = Logger.getLogger(WechatBindController.class);
	
	@Resource
	private HibernateDao hibernate;
	
	@Resource
	private BaseDao baseDao;
	
	public HibernateDao getHibernate() {
		return hibernate;
	}

	public void setHibernate(HibernateDao hibernate) {
		this.hibernate = hibernate;
	}


	/** 
	* @Title: redirect 
	* @Description: 传入需要获取openid的URL，该链接会跳转到对应的地址，并传递过去一个code参数，通过WeChatUtil中的方法，根据code获取openid和access_token
	* @param request 
	* @param response
	* @param redirectUrl
	* @throws IOException
	*/
	@RequestMapping("/redirect")
	public void redirect(HttpServletResponse response) throws IOException{
		log.debug("generate the wechat redirect url ,get the code to get user openid ");
		String url = WechatUtil.generateGetCodeUrl(SysConfig.instance().getServerUrl() + "/wechat/bindcheck");
		response.sendRedirect(url);
	}
	
	/** 
	* @Title: checkBindWechat 
	* @Description: 检查微信用户的绑定状态，如果没有绑定则跳转到绑定选择页面，如果已经绑定，则跳转到对应身份的主页
	* @param request
	* @return ModelAndView
	 * @throws Exception 
	*/
	@RequestMapping("/bindcheck")
	@Transactional
	public ModelAndView checkBindWechat(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		try {
			String code = request.getParameter("code");
			String openid = WechatUtil.getOpenidAndAccesstoken(code).getString("openid");
			/* 获取到用户的openid后，在数据库中查询与该openid绑定的教职工的信息，查询教职工设置该教职工登录成功，并返回到主页
			 * 如果为找到该用户绑定信息，则跳转到用户绑定界面
			 */
			if (openid != null) {
				TWechatBind bind = new TWechatBind();
				bind.setOpenid(openid);
				List<TWechatBind> ls = hibernate.findByExample(bind);
				if (ls.size() == 0) { // 未找到与该微信号绑定的信息
					mv.setViewName("bind/bind");
					mv.addObject("openid", openid);
				}else{
					bind = ls.get(0);
					String username = bind.getUsername();
					TSysUser userTemp = new TSysUser();
					userTemp.setUsername(username);
					List<TSysUser> userlist = hibernate.findByExample(userTemp);
					TSysUser user = userlist.get(0);
					String password = user.getPassword() + "_external";
					/* 
					 * 调用系统方法登录username 
					 * */
 					Integer sf = bind.getSf();
 					SysConfig sys = SysConfig.instance();
 					String url = sys.getCasServerLoginUrl();
 					mv.addObject("auto", true);
 					mv.addObject("autoType", "wechat");
 					mv.addObject("username", username);
 					mv.addObject("password", password);
					switch (sf) {
						case 1:
							mv.addObject("service", sys.getServerUrl()+"/teacher/index.jsp");
							mv.setViewName("redirect:"+url);
							break;
						case 2:
							mv.addObject("service", sys.getServerUrl()+"/student/index.jsp");
							mv.setViewName("redirect:"+url);
							break;
						case 3:
							mv.addObject("service", sys.getServerUrl()+"/parent/index.jsp");
							mv.setViewName("redirect:"+url);
							break;
						default:
							mv.setViewName("index");
							break;
					}
				}
			}else{
				mv.setViewName("index");
			}
		} catch (IOException e) {
			log.error("获取用户的openid出错");
			e.printStackTrace();
			mv.setViewName("index");
		}
		return mv;
	}
	
	@RequestMapping("/binduserinfo")
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
				//删除已经绑定的用户，防止多个教职工的微信绑定到同一个职工信息
				TWechatBind temp = new TWechatBind();
				temp.setOpenid(openid);
				List<TWechatBind> bindedUser = hibernate.findByExample(temp);
				for (int i = 0; i < bindedUser.size(); i++) {
					hibernate.delete(bindedUser.get(i));
				}
				
				/*开始执行绑定操作将用户和微信的openID进行绑定，下次登录的时候直接进入系统而不需要认证*/
				user = WechatUtil.fillWechatUserInfo(user);
				
				int sf = 0;
				//开始判断学生身份
				String countsql = "select * from t_stu t where t.no_ = '"+username+"'";
				int count = baseDao.queryForInt(countsql);
				if(count > 0){
					sf = 2;
				}else{
					//开始判断教职工身份
					countsql = "select * from t_tea t where t.tea_no = '"+username+"'";
					count = baseDao.queryForInt(countsql);
					if(count > 0){
						sf = 1;
					}
				}
				user.setSf(sf);
				try {
					hibernate.save(user);
					log.debug("++教职工["+user.getUsername()+"]登录微信绑定了个人服务++" );
					success = true;
				} catch (Exception e) {
					e.printStackTrace();
					result.put("eroormsg", "系统错误，绑定失败，请重试！");
				}  
				
			}else{
				result.put("eroormsg", "用户名或者密码错误！");
				log.debug("++教职工["+user.getUsername()+"]绑定微信号失败，用户名或密码错误++" );
			}
		}else{
			result.put("eroormsg", "用户名或者密码错误！");
			log.debug("++教职工["+user.getUsername()+"]绑定微信号失败，系统中未找该用户++" );
		}
		result.put("success", success);
		return result;
	}

	@RequestMapping("/unbindredirect")
	public void unbindredirect(HttpServletResponse response) throws IOException{
		log.debug("generate the wechat redirect url ,get the code to get user openid ");
		String url = WechatUtil.generateGetCodeUrl(SysConfig.instance().getServerUrl() + "/wechat/unbind");
		response.sendRedirect(url);
	}
	
	@RequestMapping("/unbind")
	public void unbind(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String delsql ="";
		String code = request.getParameter("code");
		if(code != null && !code.equals("")){
			String openid = WechatUtil.getOpenidAndAccesstoken(code).getString("openid");
			delsql = "DELETE FROM T_WECHAT_BIND T WHERE T.OPENID='"+openid+"'";
		}else{
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			String username = principal.getName();
			delsql = "DELETE FROM T_WECHAT_BIND T WHERE T.USERNAME='"+username+"'";
			
		}
		baseDao.delete(delsql);
		SysConfig config = SysConfig.instance();
		String url = config.getCasServerUrl() +"/logout?service="+config.getServerUrl()+"/wechat/redirect";
		response.sendRedirect(url);
	}
	
	/** 
	 * @Title: createMenuExample 
	 * @Description: 用来生成微信的菜单的例子
	 * @param @throws WeChatException
	 * @return void
	 */
	@RequestMapping("/createMenu")
	@ResponseBody
	public HttpResult createMenuExample() throws WeChatException{
		HttpResult result = new HttpResult();
		try{
			MenuManager mm = new MenuManager();
			Menu m = new Menu();
			List<MenuButton> btns = new ArrayList<MenuButton>();
	
			MenuButton btn1 = new MenuButton();
			btn1.setName("掌上校园");
			btn1.setType(EventType.click);
			List<MenuButton> ls = new ArrayList<MenuButton>();
			MenuButton mb1 = new MenuButton();
			mb1.setName("微信绑定");
			mb1.setType(EventType.view);
			mb1.setUrl(SysConfig.instance().getServerUrl()+ "/wechat/redirect");
			MenuButton mb2 = new MenuButton();
			mb2.setName("校园地图");
			mb2.setType(EventType.view);
			mb2.setUrl(SysConfig.instance().getServerUrl()+ "/wechat/map/map.jsp");
			ls.add(mb1);
			ls.add(mb2);
			btn1.setSubButton(ls);
	
			MenuButton btn2 = new MenuButton();
			btn2.setName("教师服务");
			btn2.setType(EventType.click);
			List<MenuButton> ls2 = new ArrayList<MenuButton>();
			MenuButton mb3 = new MenuButton();
			mb3.setName("查今日课程");
			mb3.setType(EventType.click);
			mb3.setKey("teacher_today_course");
			MenuButton mb4 = new MenuButton();
			mb4.setName("查预警信息");
			mb4.setType(EventType.click);
			mb4.setKey("teacher_warning");
			ls2.add(mb3);
			ls2.add(mb4);
			btn2.setSubButton(ls2);
			
			MenuButton btn3 = new MenuButton();
			btn3.setName("学生服务");
			btn3.setType(EventType.click);
			List<MenuButton> ls3 = new ArrayList<MenuButton>();
			MenuButton mb5 = new MenuButton();
			mb5.setName("查今日课程");
			mb5.setType(EventType.click);
			mb5.setKey("student_today_course");
			MenuButton mb6 = new MenuButton();
			mb6.setName("查上学期成绩");
			mb6.setType(EventType.click);
			mb6.setKey("student_score");
			MenuButton mb7 = new MenuButton();
			mb7.setName("查昨日消费");
			mb7.setType(EventType.click);
			mb7.setKey("student_card");
			MenuButton mb8 = new MenuButton();
			mb8.setName("查空教室");
			mb8.setType(EventType.click);
			mb8.setKey("classroom");
			ls3.add(mb5);
			ls3.add(mb6);
			ls3.add(mb7);
			ls3.add(mb8);
			btn3.setSubButton(ls3);
			
			btns.add(btn1);
			btns.add(btn2);
			btns.add(btn3);
			m.setButton(btns);
			mm.create(m);
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			result.setErrmsg("初始化菜单失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	/** 
	* @Description: 模板消息测试
	* @return: JSONObject
	*/
	@RequestMapping("/template")
	@ResponseBody
	public JSONObject testTemplateMessage(){
		TemplateMessage message = new TemplateMessage();
		message.setTouser("owN2uwyJrP9foLqDz8Ejc3KJQ8pg");
		message.setTemplate_id("ZnKXNqHF6AfysYXIj5oLc28mEH3LT-LLtPi3rydgcHM");
		Map<String, String> name = new HashMap<String, String>();
		Map<String, String> sno = new HashMap<String, String>();
		Map<String, String> score = new HashMap<String, String>();
		name.put("value", "孙伟光");
		name.put("color", "#ff5722");
		sno.put("value", "20094070218");
		sno.put("color", "#ff5722");
		score.put("value", "语文 ：30\n数学 ：28\n数学 ：28\n数学 ：28\n数学 ：28\n数学 ：28\n数学 ：28\n数学 ：28\n数学 ：28");
		score.put("color", "#ff5722");
		JSONObject data = new JSONObject();
		data.put("name", name);
		data.put("sno", sno);
		data.put("score", score);
		message.setData(data);
		return TemplateMessageUtil.sendTemplateMessage(message);
	}
}