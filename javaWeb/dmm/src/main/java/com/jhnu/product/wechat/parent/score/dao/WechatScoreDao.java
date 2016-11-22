package com.jhnu.product.wechat.parent.score.dao;

import java.util.List;
import java.util.Map;

import com.jhnu.product.wechat.parent.score.entity.CourseScore;
import com.jhnu.product.wechat.parent.score.entity.TlWechatScoreCourse;
import com.jhnu.product.wechat.parent.score.entity.TlWechatScoreRank;

public interface WechatScoreDao {
	/**
	 * 根据学生id获取学生成绩统计结果
	 * @return
	 */
	public List<Map<String,Object>> getScoreAnalyzeData(String stuId);
	/**
	 * 依据学生id获取指定学年学期的学生课程成绩信息。
	 * @param stuId
	 * @param schoolYear
	 * @param termCode
	 * @return
	 */
	public List<Map<String,Object>> getScoreCourseData(String stuId,String schoolYear,String termCode);
	/**
	 * 根据学生id获取对应班级的班级人数
	 * @param stuId
	 * @return
	 */
	public int getClassStuCount(String stuId);
	/**
	 * 根据学生id获取对应专业的班级人数
	 * @param stuId
	 * @return
	 */
	public int getMajorStuCount(String stuId);
	/**
	 * 根据学生id获取历年学期的综合成绩
	 * @param stuId
	 * @return
	 */
	public List<Map<String,Object>> getCountScoreLine(String stuId);
	/**
	 * 根据学生id获取历年学期的平均成绩
	 * @param stuId
	 * @return
	 */
	public List<Map<String,Object>> getAvgScoreLine(String stuId);
	
	/**
	 * 获取所有学生的每学年学期的总成绩、平均成绩
	 * @param shcoolYear
	 * @param termCode
	 * @return
	 */
	public List<TlWechatScoreRank> getAllStusSumAvgScore(String schoolYear,String termCode);
	/**
	 * 保存所有学生的每学年学期的总成绩、平均成绩
	 * @param result
	 */
	public void saveAllStusSumAvgScore2Log(List<TlWechatScoreRank> result);
	/**
	 * 根据学年学期获取成绩对象。
	 * @param schoolYear
	 * @param termCode
	 * @return
	 */
	public List<CourseScore> getCourseScores(String schoolYear,String termCode);
	/**
	 * 保存所有学生的最高科目、最低科目及不及格科目分数
	 * @param result
	 */
	public void saveAllStusCourse2Log(List<TlWechatScoreCourse> result);
	/**
	 * 根据学年学期获得 每个专业的每门课的最高成绩。
	 * @param schoolYear
	 * @param termCode
	 * @return
	 */
	public List<Map<String,Object>> getMajorCourseMaxScore(String schoolYear,String termCode);
	/**
	 * 获取所有行政班级的年级数据
	 * @return
	 */
	public List<Map<String,Object>> getAllClassGrade();
}
