package cn.gilight.personal.social.liao.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.syspermiss.util.ContextHolderUtils;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.HttpResult;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.personal.social.liao.service.FriendService;
import cn.gilight.personal.social.liao.service.PersonalService;
import cn.gilight.personal.social.liao.service.TopicService;

@Controller("socialPersonalController")
@RequestMapping("/social/personal")
public class PersonalController {
	private Logger log = Logger.getLogger(PersonalController.class);
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernate;
	@Resource
	private FriendService friendService;
	@Resource
	private TopicService topicService;
	@Resource
	private PersonalService personalService;
	
	@RequestMapping("queryPersonalInfo")
	@ResponseBody
	public HttpResult queryPersonalInfo(String username){
		if(!StringUtils.hasText(username)){
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			username = principal.getName();
		}
		HttpResult result = new HttpResult();
		try{
			result.setResult(personalService.queryInfoOfPerson(username));
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			log.error(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("queryCard")
	@ResponseBody
	public HttpResult queryCard(String username){
		HttpResult result = new HttpResult();
		try{
			result.setResult(personalService.queryCard(username));
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			log.error(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("queryBook")
	@ResponseBody
	public HttpResult queryBook(String username){
		HttpResult result = new HttpResult();
		try{
			result.setResult(personalService.queryBook(username));
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			log.error(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("queryCourse")
	@ResponseBody
	public HttpResult queryCourse(String username){
		HttpResult result = new HttpResult();
		try{
			result.setResult(personalService.queryCourse(username));
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			log.error(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("queryMyUnreadMessageNum")
	@ResponseBody
	public HttpResult queryMyUnreadMessageNum(){
		HttpResult result = new HttpResult();
		try{
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			String username = principal.getName();
			int nums = 0;
			nums += friendService.queryFriendApplyMessageOfUser(username).size();
			nums += topicService.queryUnReadMessageOfUser(username).size();
			result.setResult(nums);
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			log.error(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("queryMyUnreadMessage")
	@ResponseBody
	public HttpResult queryMyUnreadMessage(){
		HttpResult result = new HttpResult();
		try{
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			String username = principal.getName();
			Map<String, Object> message = new HashMap<String, Object>();
			//好友请求消息
			message.put("friendMessage",friendService.queryFriendApplyMessageOfUser(username));
			friendService.changeFriendUnreadMessageState(username);
			
			//帖子回复消息
			message.put("topicMessage", topicService.queryUnReadMessageOfUser(username));
			topicService.changeUnreadMessageStateOfUser(username);
			
			result.setResult(message);
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			log.error(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("queryMyFriendAndTopicNums")
	@ResponseBody
	public HttpResult queryMyFriendAndTopicNums(){
		HttpResult result = new HttpResult();
		try{
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			String username = principal.getName();
			Map<String, Object> message = new HashMap<String, Object>();
			//好友数量
			message.put("friendNum",friendService.queryFriendNum(username));
			//帖子数量
			message.put("topicNum", topicService.queryTopicNumOfUser(username));
			result.setResult(message);
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			log.error(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("queryTopicNumOfToday")
	@ResponseBody
	public HttpResult queryTopicNumOfToday(){
		HttpResult result = new HttpResult();
		try{
			result.setResult(topicService.queryTopicNumOfToday());
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			log.error(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("/{username}/main")
	public ModelAndView personalHomePage(@PathVariable("username") String username) {
		ModelAndView mv = new ModelAndView();
		//检查用户是否存在
		if(personalService.isPersonExist(username)){
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			String loginUser = principal.getName();
			//如果是当前登录用户，则跳转到个人页面
			mv.addObject("username", username);
			if(username.equals(loginUser)){
				mv.setViewName("social/liao/personal/mine");
			}else 
				//如果是好友关系，跳转到好友信息查看页面
				if(friendService.isFriend(username, loginUser)){
					mv.setViewName("social/liao/personal/other");
				}else{
					//跳转到非好友关系提醒
					mv.setViewName("social/liao/personal/notfriend");
				}
		}else{
			mv.setViewName("404");
		}
		return mv;
	}
}