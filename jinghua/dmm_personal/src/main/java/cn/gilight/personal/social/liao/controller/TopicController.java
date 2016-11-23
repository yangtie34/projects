package cn.gilight.personal.social.liao.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.jhnu.syspermiss.util.ContextHolderUtils;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.HttpResult;
import cn.gilight.personal.po.TLiaoTopic;
import cn.gilight.personal.po.TLiaoTopicImage;
import cn.gilight.personal.po.TLiaoTopicReply;
import cn.gilight.personal.po.TLiaoTopicReplyAnswer;
import cn.gilight.personal.social.liao.service.TopicService;

@Controller("socialTopicController")
@RequestMapping("/social/topic")
public class TopicController {
	private Logger log = Logger.getLogger(TopicController.class);
	@Resource
	private HibernateDao hibernate;
	
	@Resource
	private BaseDao baseDao;
	
	@Resource
	private TopicService topicService;
	
	@RequestMapping("/save")
	@ResponseBody
	@Transactional
	public HttpResult saveTopic(TLiaoTopic topic,String images){
		HttpResult result = new HttpResult();
		try {
			//保存帖子信息
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			String username = principal.getName();
			topic.setUsername(username);
			topic.setState(true);
			topic.setCreateTime(new Date());
			hibernate.save(topic);
			
			//如果有图片保存
			JSONArray imagesList = JSONArray.parseArray(images);
			if(imagesList.size() > 0){
				List<TLiaoTopicImage> imagesStore = new ArrayList<TLiaoTopicImage>();
				for (int i = 0; i < imagesList.size(); i++) {
					TLiaoTopicImage topicImage = new TLiaoTopicImage();
					topicImage.setTopicId(topic.getId());
					topicImage.setImgUrl(imagesList.getString(i));
					topicImage.setCreateTime(new Date());
					imagesStore.add(topicImage);
				}
				hibernate.saveAll(imagesStore);
			}
			result.setSuccess(true);
		} catch (Exception e) {
			 result.setSuccess(false);
			 log.error(e.getMessage());
			 result.setErrmsg("保存失败，请重试");
		}
		return result;
	}
	
	@RequestMapping("/queryAll")
	@ResponseBody
	public HttpResult queryTopicListByPage(Page page){ 
		HttpResult result = new HttpResult();
		try {
			result.setResult(topicService.queryTopicListByPage(page));
			result.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/queryAllOfFriend")
	@ResponseBody
	public HttpResult queryTopicListOfMyFriendAndMeByPage(Page page){
		HttpResult result = new HttpResult();
		try {
			result.setResult(topicService.queryTopicListOfMyFriendAndMeByPage(page));
			result.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/queryMyTopicList")
	@ResponseBody
	public HttpResult queryMyTopicList(Page page){ 
		HttpResult result = new HttpResult();
		try {
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			String username = principal.getName();
			result.setResult(topicService.queryTopicListOfUserByPage(page,username));
			result.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/queryTopicInfo")
	@ResponseBody
	public HttpResult queryTopicInfo(String id){ 
		HttpResult result = new HttpResult();
		try {
			result.setResult(topicService.queryTopicInfo(id));
			result.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/deleteTopic")
	@ResponseBody
	@Transactional
	public HttpResult deleteTopic(String id){ 
		HttpResult result = new HttpResult();
		try {
			topicService.deleteTopic(id);
			result.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/saveReply")
	@ResponseBody
	@Transactional
	public HttpResult saveReply(String topicId,String commentContent,Boolean returnAll){ 
		HttpResult result = new HttpResult();
		try {
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			String username = principal.getName();
			TLiaoTopicReply reply = new TLiaoTopicReply();
			reply.setTopicId(topicId);
			reply.setContent(commentContent);
			reply.setUsername(username);
			reply.setState(false);
			reply.setCreateTime(new Date());
			hibernate.save(reply);
			if (returnAll ) {
				result.setResult(topicService.queryCommentsOfTopic(topicId));
			}else{
				result.setResult(topicService.queryTop5CommentsOfTopic(topicId));
			}
			result.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/saveReplyAnswer")
	@ResponseBody
	@Transactional
	public HttpResult saveReplyAnswer(String topicId,String replyId,String toUsername,String commentContent,Boolean returnAll){ 
		HttpResult result = new HttpResult();
		try {
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			String username = principal.getName();
			TLiaoTopicReplyAnswer answer = new TLiaoTopicReplyAnswer();
			answer.setReplyId(replyId);
			answer.setState(false);
			answer.setUsername(username);
			answer.setToUsername(toUsername);
			answer.setCreateTime(new Date());
			answer.setContent(commentContent);
			hibernate.save(answer);
			if (returnAll ) {
				result.setResult(topicService.queryCommentsOfTopic(topicId));
			}else{
				result.setResult(topicService.queryTop5CommentsOfTopic(topicId));
			}
			result.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
}