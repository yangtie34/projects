package cn.gilight.personal.social.liao.controller;


import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhnu.syspermiss.util.ContextHolderUtils;

import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.HttpResult;
import cn.gilight.personal.po.TLiaoRelation;
import cn.gilight.personal.po.TLiaoRelationApply;
import cn.gilight.personal.social.liao.service.FriendService;

@Controller("socialFriendController")
@RequestMapping("/social/friend")
public class FriendController {
	private Logger log = Logger.getLogger(FriendController.class);
	@Resource
	private FriendService friendService;
	@Resource
	private HibernateDao hibernate;
	
	@RequestMapping("queryMyFriendList")
	@ResponseBody
	public HttpResult queryMyFriendList(){
		HttpResult result = new HttpResult();
		try{
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			String username = principal.getName();
			result.setResult(friendService.queryFriendList(username));
			result.setSuccess(true);
		}catch(Exception e ){
			result.setSuccess(false);
			log.error(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("delFriendShip")
	@ResponseBody
	@Transactional
	public HttpResult delFriendShip(String id){
		HttpResult result = new HttpResult();
		try{
			friendService.delFriendShip(id);
			result.setSuccess(true);
		}catch(Exception e ){
			result.setSuccess(false);
			log.error(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("searchUserList")
	@ResponseBody
	@Transactional
	public HttpResult searchUserList(Page page,String searchText){
		HttpResult result = new HttpResult();
		try{
			result.setResult(friendService.searchUserList(page,searchText));
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			log.error(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("sendFriendApply")
	@ResponseBody
	@Transactional
	public HttpResult sendFriendApply(String targetUsername){
		HttpResult result = new HttpResult();
		try{
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			String username = principal.getName();
			TLiaoRelationApply apply = new TLiaoRelationApply();
			apply.setUsername(username);
			apply.setTargetUsername(targetUsername);
			apply.setCreateTime(new Date());
			apply.setState(0);
			hibernate.save(apply);
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			log.error(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("passFriendApply")
	@ResponseBody
	@Transactional
	public HttpResult passFriendApply(String id){
		HttpResult result = new HttpResult();
		try{
			//设置请求状态
			TLiaoRelationApply apply = hibernate.getById(id, TLiaoRelationApply.class);
			apply.setState(1);
			hibernate.update(apply);
			
			//保存好友关系
			TLiaoRelation relation = new TLiaoRelation();
			relation.setApplyId(id);
			relation.setUsernamea(apply.getUsername());
			relation.setUsernameb(apply.getTargetUsername());
			relation.setCreateTime(new Date());
			hibernate.save(relation);
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			log.error(e.getMessage());
		}
		return result;
	}
	
	
	
	@RequestMapping("ignoreFriendApply")
	@ResponseBody
	@Transactional
	public HttpResult ignoreFriendApply(String id){
		HttpResult result = new HttpResult();
		try{
			TLiaoRelationApply apply = hibernate.getById(id, TLiaoRelationApply.class);
			apply.setState(2);
			hibernate.update(apply);
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			log.error(e.getMessage());
		}
		return result;
	}
}