package cn.gilight.personal.student.four.service;

import java.util.List;
import java.util.Map;

public interface FourScoreService {
	
	/**
	 * 查询学生成绩
	 * @param username
	 * @return
	 */
	public Map<String,Object> getScoreMap(String username);

	/**
	 * 查询学生成绩曲线图
	 * @param username
	 * @return
	 */
	public List<Map<String, Object>> getScoreChart(String username);
	
	/**
	 * 查询成绩最好的学年学期及成绩
	 * @param username
	 * @return
	 */
	public Map<String,Object> getGoodScore(String username);

	/**
	 * 查询成绩最好的课
	 * @param username
	 * @return
	 */
	public Map<String,Object> scoreCourseMap(String username);
	

}
