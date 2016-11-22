package com.jhnu.person.tea.dao;

import java.util.List;
import java.util.Map;

import com.jhnu.system.common.page.Page;

public interface TeaTeachDao {
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
	public List skjd(String tea_id);
	/**
	 * 授课成绩
	 * @param id
	 * @return
	 */
	public Page skcj(String id, int currentPage, int numPerPage);
	
	
	
	//--------
	/**
	 * 获取今日课程
	 */
	public List<Map<String,Object>> getTodayClass(String school_year,String term_code,String tea_id,int zc,int week);
	
	/**
	 * 获取今日的课程明细
	 * @param courseArrangementId 课程安排ID
	 * @return
	 */
	public List<Map<String,Object>> getTodayCourse(String courseArrangementId);
	/**
	 * 获取课表
	 */
	public List<Map<String, Object>> getTermClass(String school_year,String term_code,String tea_id);
}
