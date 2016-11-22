package com.jhkj.mosdc.sc.service;

public interface OaService {
	//登录次数统计
	/**
	 * 获取总登录记录统计
	 * @return
	 */
	public String getAllLoginLog(String params);
	
	/**
	 * 获取某段时间登录记录统计
	 * @return
	 */
	public String getLoginLog(String params);
	
	/**
	 * 获取所有用户某时间段登录次数排名
	 * @return
	 */
	public String getAllUserLoginCounts(String params);
	
	//公告查看次数统计
	/**
	 * 获取各部门某时间段查看公告次数排名
	 * @return
	 */
	public String getCheckByDept(String params);

	/**
	 * 查询某时间段的公告
	 * @return
	 */
	public String getAllNotice(String params);
	
	/**
	 * 根据公告ID获取各部门在某时间段查看该公告次数排名
	 * @param notice_id
	 * @return
	 */
	public String getNoticeCheckByDept(String params);
	
	/**
	 * 获取每个职工公告查看条数和未查看条数
	 * @param user_id
	 * @return
	 */
	public String getNoticeCountsByUser(String params);
	
	
	//表单使用情况统计
	/**
	 * 查询所有表单的使用情况
	 * @param fromTime
	 * @param endTime
	 * @return
	 */
	public String getFormUse(String params);
	
}
