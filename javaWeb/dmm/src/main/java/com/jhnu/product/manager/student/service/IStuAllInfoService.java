package com.jhnu.product.manager.student.service;

import java.util.List;
import java.util.Map;

/**
 * @title 学生全景信息Service
 * @description 学生全景信息
 * @author Administrator
 * @date 2015/10/28 14:43
 */
public interface IStuAllInfoService {

	/**
	 * 获取学生学籍信息
	 * 
	 * @param stu_id
	 * @return
	 */
	public List<Map<String, Object>> getStuInfo(String stu_id);

	/**
	 * @description 学生的违纪处分
	 * @param stuId
	 *            学生的学号
	 * @return Map
	 */
	public Map<String, Object> stusPunishmentViolation(String stuId);

	/**
	 * @description 学生的文化成绩
	 * @param stu_id
	 *            学生的学号
	 * @param termCode
	 *            学期Code
	 * @param schoolYear
	 *            学年
	 * @return Map
	 */
	public Map<String, Object> stusCultureScore(String stu_id, String termCode, String schoolYear);

	/**
	 * 获取荣誉信息(奖学金)
	 */
	public List<Map<String, Object>> getAward(String stu_id);

	/**
	 * 获取资助信息(助学金)
	 */
	public List<Map<String, Object>> getSubsidy(String stu_id);

	/**
	 * 获取室友信息
	 * 
	 * @param stu_id
	 * @return
	 */
	public List<Map<String, Object>> getRoommate(String stu_id);

	/**
	 * 从临时表中获取某学生的总消费金额和日均消费
	 * 
	 * @param stu_id
	 * @return
	 */
	public Map<String, Object> getCardLog(String stu_id);

	/**
	 * 保存学生的总消费金额和日均消费
	 * 
	 * @return
	 */
	public void saveCard();

	/**
	 * 获取某学生某学期的消费情况柱状图数据
	 * 
	 * @param stu_id
	 * @param school_year
	 * @param term_code
	 * @return
	 */
	public List<Map<String, Object>> getCardByYear(String stu_id, String school_year, String term_code);

	/**
	 * 获取请销假信息
	 * 
	 * @param stu_id
	 * @return
	 */
	public List<Map<String, Object>> getLeaveByStu(String stu_id);

	/**
	 * 获取请销假统计信息
	 * 
	 * @param stu_id
	 * @return
	 */
	public List<Map<String, Object>> getLeaveInfoByStu(String stu_id);

	/**
	 * 获取图书借阅信息
	 * 
	 * @param stu_id
	 * @return
	 */
	public List<Map<String, Object>> getBookBorrowByStu(String stu_id);

	/**
	 * 获取学生的课表
	 * 
	 * @param stu_id
	 * @return
	 */
	public List<Map<String, Object>> getCoureArrangementByStu(String stu_id, String zc);

	/**
	 * 获取课表的初始化数据
	 * 
	 * @param year
	 * @param term
	 * @return
	 */
	public List<String> getCoureArrangementInitByStu(String year, String term, String zc);

	/**
	 * 保存人均消费到临时表
	 * 
	 * @param school_year
	 * @param term_code
	 */
	public void saveStuAllCard(String school_year, String term_code);

	/**
	 * @description 该学生成绩与班级和专业平均成绩对比
	 * @param stu_id
	 *            学生id
	 * @param termCode
	 *            学期code
	 * @param schoolYear
	 *            学年(eg:'2011-2012')
	 * @return
	 */
	public Map<String, Object> oneStuCompareClassAndMajor(String stu_id, String termCode, String schoolYear);
	

	/**
	 * @description 某学生在班级和专业中的排名
	 * @param stuId 学生No
	 * @param termCode 学期code
	 * @param schoolYear 学年(eg:'2011-2012')
	 * @return Map
	 */
	public Map<String,Object> stuClassMajorRank(String stuId,
			String termCode, String schoolYear);
	
	

}
