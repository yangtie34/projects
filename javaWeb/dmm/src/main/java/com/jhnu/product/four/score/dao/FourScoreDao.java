package com.jhnu.product.four.score.dao;

import java.util.List;
import java.util.Map;

import com.jhnu.system.common.page.Page;

public interface FourScoreDao {
	/**
	 * 获取平均成绩
	 * @return
	 */
	public List<Map<String,Object>> getAvgScore();
	
	/**
	 * 将平均成绩保存至LOG
	 * @param list
	 */
	public void saveAvgScoreLog(List<Map<String,Object>> list);
	
	/**
	 * 通过LOG获取平均成绩
	 * @param id 学生ID
	 * @return
	 */
	public List<Map<String,Object>> getAvgScoreLog(String id);
	
	/**
	 * 获取总成绩
	 * @return
	 */
	public List<Map<String,Object>> getSumScore();
	
	/**
	 * 将总成绩保存至LOG
	 * @param list
	 */
	public void saveSumScoreLog(List<Map<String,Object>> list);
	
	/**
	 * 通过LOG获取总成绩
	 * @param id 学生ID
	 * @return
	 */
	public List<Map<String,Object>> getSumScoreLog(String id);
	
	/**
	 * 分专业和毕业年获取所有学生的平均成绩
	 * @return
	 */
	public List<Map<String,Object>> getAllAvgScore();
	
	/**
	 * 获取最高成绩
	 * @return
	 */
	public List<Map<String,Object>> getBestScore();
	
	/**
	 * 将最高成绩保存至LOG
	 * @param list
	 */
	public void saveBestScoreLog(List<Map<String,Object>> list);
	
	/**
	 * 通过LOG获取最高成绩
	 * @param id 学生ID
	 * @return
	 */
	public List<Map<String,Object>> getBestScoreLog(String id);
	
	/**
	 * 分学年学期获取考试排名
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return
	 */
	public List<Map<String,Object>> getRankByTime(String schoolYear,String termCode);
	
	/**
	 * 将分学年学期获取考试排名保存至LOG
	 * @param list
	 */
	public void saveRankLog(List<Map<String,Object>> list);
	
	/**
	 * 获取历史考试名次
	 * @param id 学生ID
	 * @return
	 */
	public List<Map<String, Object>> getRankLogByStuId(String id);
	
	/**
	 * 获取考试科目和成绩列表
	 * @param id 学生ID
	 * @param schoolYear 学年
	 * @param termCode	学期
	 * @return
	 */
	public List<Map<String, Object>> getScoureList(String id,String schoolYear,String termCode);
	
	/**
	 * 获取分专业最好科目排名
	 * @return
	 */
	public Page getBsetCourseByMajor(int currentPage,int numPerPage,Integer totalRows,Integer status);
	
	/**
	 * 将分专业最好科目排名保存至LOG
	 * @param list
	 */
	public void saveBsetCourseByMajorLog(List<Map<String, Object>> list,boolean isFrist);
	
	/**
	 * 从LOG中获取分专业最好科目排名
	 * @param endYear 毕业年
	 * @param MajorId 专业ID
	 * @param order 前几名
	 * @return
	 */
	public List<Map<String,Object>> getBsetCourseByMajorLog(String endYear,String MajorId,int order);
	
	/**
	 * 获取分学生最好科目排名
	 * @return
	 */
	public Page getBsetCourseByStu(int currentPage,int numPerPage,Integer totalRows,Integer status);
	
	/**
	 * 将分学生最好科目排名保存至LOG
	 * @param list
	 */
	public void saveBsetCourseByStuLog(List<Map<String, Object>> list,boolean isFrist);
	
	/**
	 * 从LOG中获取分学生最好科目排名
	 * @param id 学生ID
	 * @param order 前几名
	 * @return
	 */
	public List<Map<String,Object>> getBsetCourseByStuLog(String id,int order);
	
	/**
	 * 获取第一次挂科
	 * @return
	 */
	public List<Map<String,Object>> getFirstDown();
	
	/**
	 * 将第一次挂科保存至LOG
	 * @param list
	 */
	public void saveFirstDownLog(List<Map<String,Object>> list);
	
	/**
	 * 通过LOG获取第一次挂科
	 * @param id 学生ID
	 * @return
	 */
	public List<Map<String,Object>> getFirstDownLog(String id);
	
	/**
	 * 创建平均成绩临时表数据
	 */
	public void createTempScoreAvg();
	/**
	 * 删除平均成绩临时表数据
	 */
	public void dropTempScoreAvg();
	/**
	 * 从临时表中获取学生平均成绩
	 * @return
	 */
	public List<Map<String,Object>> getScoreAvgFromTemp();
	/**
	 * 保存学生平均成绩
	 * @return
	 */
	public void saveScoreAvgLog(List<Map<String,Object>> list);
	/**
	 * 根据学生id获取四年平均成绩数据
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getScoreStuLine(String id);
	/**
	 * 根据年级、专业id获取四年平均成绩数据
	 * @param majorId
	 * @param enrollGrade
	 * @return
	 */
	public List<Map<String,Object>> getScoreMajorLine(String majorId,String enrollGrade);
	/**
	 * 根据学生id获取四年综合绩点成绩
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getGPAScoreLog(String id);
	/**
	 * 获取学生综合绩点成绩
	 * @return
	 */
	public List<Map<String,Object>> getGPAScore();
	/**
	 * 保存学生综合绩点成绩到LOG
	 * @param scores
	 */
	public void saveGPAScore(List<Map<String,Object>> scores);
	
	public Page getScoreDetailLog(int currentPage,int numPerPage,String tj);
	
	public List<Map<String, Object>> getScoreDetailGroupBySY(String id);
	
}
