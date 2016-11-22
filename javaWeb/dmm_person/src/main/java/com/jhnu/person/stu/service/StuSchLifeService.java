package com.jhnu.person.stu.service;

import java.util.List;

import com.jhnu.system.common.page.Page;

public interface StuSchLifeService {
	/**
	 * 一卡通消费+余额
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @return
	 */	
	public List yktxf(String id,String startTime,String endTime);
	/**
	 * 一卡通充值记录
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @return
	 */	
	public List yktcz(String id,String startTime,String endTime);
	/**
	 * 一卡通消费分析
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @return
	 */	
	public List yktxffx(String id,String startTime,String endTime);
	/**
	 * 一卡通消费明细
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @return
	 */	
	public Page yktxfmx(String id,String startTime,String endTime,
			int currentPage, int numPerPage);
	/**
	 * 网络生活-平均上网时间
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @return
	 */	
	public List ieAvgTime(String id,String startTime,String endTime);
	/**
	 * 网络生活-上网时间
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @return
	 */	
	public List ieAllTime(String id,String startTime,String endTime);
	/**
	 * 大学首次
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @return
	 */	
	public List schFirst(String id);
}
