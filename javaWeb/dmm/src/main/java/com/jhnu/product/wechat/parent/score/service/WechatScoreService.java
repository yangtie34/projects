package com.jhnu.product.wechat.parent.score.service;

import java.util.List;
import java.util.Map;

import com.jhnu.product.common.stu.entity.Student;
import com.jhnu.product.wechat.parent.score.entity.CourseScore;
import com.jhnu.product.wechat.parent.score.entity.StuCourseScore;
import com.jhnu.product.wechat.parent.score.entity.TlWechatScoreCourse;
import com.jhnu.product.wechat.parent.score.entity.TlWechatScoreRank;
import com.jhnu.product.wechat.parent.score.entity.WechatScoreData;
import com.jhnu.system.common.chart.Chart;

public interface WechatScoreService {
	/**
	 * 依据学生id获取学生的(最近一学期的)成绩统计数据。
	 * @param stuId
	 * @return
	 */
	public WechatScoreData getWechatScoreData(String stuId);
	/**
	 * 依据学生标识 获取学生成绩曲线。
	 * @param stuId
	 * @return
	 */
	public Chart getScoreLine(String stuId);
	
	/**
	 * 获取学生每学年学期的总成绩和平均成绩。
	 * @param shcoolYear
	 * @param termCode
	 * @return
	 */
	public List<TlWechatScoreRank> getAllStusSumAvgScore(String schoolYear,String termCode);
	
	/**
	 * 保存学生每学年学期的总成绩和平均成绩
	 * @param schoolYear
	 * @param termCode
	 */
	public void saveAllStusSumAvgScore2Log(List<TlWechatScoreRank> result);
	/**
	 * 根据学年学期获取学生及成绩 关系对象。
	 * @param schoolYear
	 * @param termCode
	 * @return
	 */
	public List<StuCourseScore> getAllStuCourseScores(List<CourseScore> css,List<Student> stus);
	/**
	 * 根据学年学期获取全校学生成绩对象
	 * @param shcoolYear
	 * @param termCode
	 * @return
	 */
	public List<CourseScore> getStuCourseScores(String shcoolYear,String termCode);
	
	/**
	 * 保存学生每学年学期的总成绩和平均成绩
	 * @param schoolYear
	 * @param termCode
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
