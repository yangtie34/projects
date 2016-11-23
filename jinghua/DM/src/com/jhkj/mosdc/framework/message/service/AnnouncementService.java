package com.jhkj.mosdc.framework.message.service;

public interface AnnouncementService {

	/**
	 * 查询最近的公告（最近10条）
	 * @param params
	 * @return
	 */
	public String queryRecentAnnouncement(String params);
	/**
	 * 查询公告
	 */
	public String queryAnnouncement(String params);
	/**
	 * 添加公告
	 * @throws Exception 
	 */
	public String addAnnouncement(String params) throws Exception;
	/**
	 * 移除公告
	 * @throws Exception 
	 */
	public String removeAnnouncement(String params) throws Exception;
	/**
	 * 修改公告
	 */
	public String updateAnnouncement(String params) throws Exception;
	
}
