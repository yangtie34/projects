package com.jhnu.person.stu.dao;

import java.util.List;
import java.util.Map;

import com.jhnu.system.common.page.Page;

public interface StuStudyDao {
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
	 * 个人课表
	 * @param id
	 * @return
	 */
	public List grkb(String id);
	/**
	 * 获取今日课程
	 * @param stu_id
	 * @return
	 */
	public List<Map<String, Object>> getTodayCourse(String stu_id);
/**
	 * 成绩查询
	 * @param id
	 * @return
	 */
	public Page skcj(String id, int currentPage, int numPerPage);
	/**
	 * 获取课表
	 */
	public List<Map<String, Object>> getTermClass(String school_year,String term_code,String tea_id,int zc);
}
