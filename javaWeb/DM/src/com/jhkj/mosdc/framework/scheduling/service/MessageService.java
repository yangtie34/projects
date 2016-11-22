package com.jhkj.mosdc.framework.scheduling.service;

/**
 * 消息处理类，用于前台信息推送
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by wangyongtai(490091105@.com)
 * @DATE:2013-12-6
 * @TIME: 上午11:19:09
 */
public interface MessageService {
	
	/**
	 * 查询当前用户需要的信息
	 * @param params
	 * @return
	 */
	public String queryMessages(String params);
	/**
	 * 把用户阅读过的信息的标识置为已读
	 * @throws Exception 
	 */
	public String updateReadYetFlag(String params) throws Exception;
}
