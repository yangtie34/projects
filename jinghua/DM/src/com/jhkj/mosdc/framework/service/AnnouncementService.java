package com.jhkj.mosdc.framework.service;

/**
 * 公告Service
 * @author Administrator
 *
 */
public interface AnnouncementService {
	
	/**
	 * 查询最新的公告
	 * @param params
	 * @return
	 */
	public String queryNewAnnouncement(String params);
	/**
	 * 查询最近十条公告
	 */
	public String queryLaterAnnouncement(String params);
	/**
	 * 根据ID获取公告
	 * @return
	 */
	public String getAnnouncement(String params);
}
