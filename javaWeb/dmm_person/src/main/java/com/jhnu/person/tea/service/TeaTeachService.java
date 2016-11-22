package com.jhnu.person.tea.service;

import java.util.List;
import java.util.Map;

import com.jhnu.system.common.page.Page;

public interface TeaTeachService {
	/**
	 * 今日课程
	 * @param id
	 * @return
	 */
	public List jrkc(String id);
	/**
	 * 个人课表
	 * @param id
	 * @return
	 */
	public List grkb(String id);
	/**
	 * 授课进度
	 * @param id
	 * @return
	 */
	public List skjd(String id);
	/**
	 * 授课成绩
	 * @param id
	 * @return
	 */
	public Page skcj(String id, int currentPage, int numPerPage);
	
}
