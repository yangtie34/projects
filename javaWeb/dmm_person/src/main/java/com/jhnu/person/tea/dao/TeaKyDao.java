package com.jhnu.person.tea.dao;

import java.util.List;
import java.util.Map;

import com.jhnu.system.common.page.Page;

public interface TeaKyDao {
	/**
	 * 推荐图书
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List pushBooks(String id, String startTime, String endTime);
	/**
	 * 借阅分类
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List jyfl(String id,String startTime,String endTime);
	/**
	 * 借阅数量
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List jysl(String id,String startTime,String endTime);
	/**
	 * 借阅明细
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Page jymx(String id,String startTime,String endTime,
			int currentPage, int numPerPage);
	/**
	 * 科研信息
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List kyxx(String id,String startTime,String endTime);

}
