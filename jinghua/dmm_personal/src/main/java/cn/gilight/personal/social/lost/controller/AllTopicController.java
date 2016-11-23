package cn.gilight.personal.social.lost.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.HttpResult;
import cn.gilight.personal.social.lost.service.AllTopicService;


@Controller("socialLostTopicController")
@RequestMapping("/social/lost/entire")
public class AllTopicController {
	
	private Logger log = Logger.getLogger(AllTopicController.class);
	@Resource
	private AllTopicService allTopicService;
	
	@RequestMapping("/queryallTopic")
	@ResponseBody
	public HttpResult queryAllTopic(Page page,String keyword,String typeCode){
		HttpResult result = new HttpResult();
		try {
			result.setResult(allTopicService.queryAllTopicByPage(page,keyword,typeCode));
			result.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
}
