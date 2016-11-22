package cn.gilight.personal.social.lost.service;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

public interface AllTopicService {

	/**
	* @Description: 查询帖子
	* @param page
	* @param keyword
	* @param typeCode
	* @return: Page 
	*/
	public abstract Page queryAllTopicByPage(Page page,String keyword,String typeCode);

}
