package com.jhnu.person.tea.dao;

import java.util.List;

import com.jhnu.system.common.page.Page;

public interface TeaSchLifeDao {
	/**
	 * 工资组成
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List gzComb(String id,String startTime,String endTime);
	/**
	 * 工资变化
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @return
	 */	
	public List gzChange(String id,String startTime,String endTime);
	/**
	 * 工资详情
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @return
	 */	
	public Page gzxq(String id,String startTime,String endTime, int currentPage, int numPerPage);
	/**
	 * 一卡通消费
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
	//一卡通余额
	List yktye(String id);
	
	
}
