package com.jhnu.product.manager.student.dao;

import java.util.List;
import java.util.Map;

public interface StuAllInfoDao {

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
	 * 获取请销假统计信息
	 * 
	 * @param stu_id
	 * @return
	 */
	public List<Map<String, Object>> getLeaveInfoByStu(String stu_id);

	/**
	 * 获取请销假信息
	 * 
	 * @param stu_id
	 * @return
	 */
	public List<Map<String, Object>> getLeaveByStu(String stu_id);

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
	 * @description 学生的文化成绩
	 * @param stu_id
	 *            学生的学号
	 * @param termCode
	 *            学期Code
	 * @param schoolYear
	 *            学年
	 * @return List<Map>
	 */
	public List<Map<String, Object>> stusCultureScore(String stu_id, String termCode, String schoolYear);

	/**
	 * 获取某学年学期的日均消费
	 * 
	 * @param stu_id
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> getCard(String startDate, String endDate);

	/**
	 * 获取全校学生某学年学期的日均消费
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map<String, Object> getAllCard(String startDate, String endDate);

	/**
	 * 获取全校男女学生某学年学期的日均消费
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> getSexCard(String startDate, String endDate);

	/**
	 * 获取学生的累计总消费
	 * 
	 * @param stu_id
	 * @return
	 */
	public List<Map<String, Object>> getTotalAndAvgMoney();

	/**
	 * 保存学生的累计总消费到临时表
	 * 
	 * @param stu_id
	 * @return
	 */
	public void saveTotalAndAvgMoney(List<Map<String, Object>> list);

	/**
	 * 获取某学生的累计总消费
	 * 
	 * @param stu_id
	 * @return
	 */
	public Map<String, Object> getTotalAndAvgMoneyLog(String stu_id);

	/**
	 * 保存学生全景的消费消费信息到临时表
	 * 
	 * @param result
	 */
	public void saveStuAllCard(List<Map<String, Object>> result);

	/**
	 * 从临时表中获取某学生某学年学期的日均消费
	 * 
	 * @param stu_id
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> getCardLog(String stuId, String startDate, String endDate);


	/**
	 * @description 学生在专业中的各成绩比较
	 * @param stu_id
	 *            学生No
	 * @param termCode
	 *            学期code
	 * @param schoolYear
	 *            学年(eg:'2012-2013')
	 * @return 学年(eg:'2012-2013')
	 */
	public List<Map<String, Object>> oneStuCompareMajor(String stu_id, String termCode, String schoolYear);

	/** 根据学年和学期获取上课的周
	 * @param schoolYear
	 * @param termCode
	 * @return
	 */
	public List<Map<String, Object>> getCourseWeeks(String schoolYear, String termCode);
	
	/**
	 * @description 学生在班级的各成绩比较
	 * @param stu_id
	 *            学生No
	 * @param termCode
	 *            学期code
	 * @param schoolYear
	 *            学年(eg:'2012-2013')
	 * @return List<Map>
	 */
	public List<Map<String, Object>> oneStuCompareClasses(String stu_id,
			String termCode, String schoolYear);


	/**
	 * @description 某个班里的人数
	 * @param stuId
	 *            学生No
	 * @return List<Map>
	 */
	public List<Map<String, Object>> stuClassNums(String stuId);

	/**
	 * @description 某个专业中的人数
	 * @param stuId
	 *            学生No
	 * @return List<Map>
	 */
	public List<Map<String, Object>> stuMajorNums(String stuId);

	/**
	 * @description 查询某学生学科成绩和名称(降序)
	 * @param stuId
	 *            学生No
	 * @param termCode
	 *            学期code
	 * @param schoolYear
	 *            学年(eg:'2012-2013')
	 * @return List<Map>
	 */
	public List<Map<String, Object>> stuScoreDescName(String stuId,
			String termCode, String schoolYear);
	
	/**
	 * @description 查询某学生学科成绩和名称(升序)
	 * @param stuId
	 *            学生No
	 * @param termCode
	 *            学期code
	 * @param schoolYear
	 *            学年(eg:'2012-2013')
	 * @return List<Map>
	 */
	public List<Map<String, Object>> stuScoreAscName(String stuId,
			String termCode, String schoolYear);

	/**
	 * @description 某学生成绩在班级中的排名
	 * @param stuId
	 *            学生No
	 * @param termCode
	 *            学期code
	 * @param schoolYear
	 *            学年(eg:'2012-2013')
	 * @return List<Map>
	 */
	public List<Map<String, Object>> stuClassRankings(String stuId,
			String termCode, String schoolYear);

	/**
	 * @description 某学生成绩在专业中的排名
	 * @param stuId
	 *            学生No
	 * @param termCode
	 *            学期code
	 * @param schoolYear
	 *            学年(eg:'2012-2013')
	 * @return List<Map>
	 */
	public List<Map<String, Object>> stuMajorRankings(String stuId,
			String termCode, String schoolYear);

}
