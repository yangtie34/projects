package com.jhnu.product.four.score.service;

import java.util.List;
import java.util.Map;

import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.system.common.chart.Chart;
import com.jhnu.system.common.condition.Condition;
import com.jhnu.system.common.page.Page;

public interface FourScoreService {
	
	/**
	 * 获取平均成绩
	 * @param id
	 * @return
	 */
	public ResultBean getAvgScoreLog(String id);
	
	/**
	 * 获取最好名次
	 * @param id
	 * @return
	 */
	public ResultBean getBestRankLog(String id);
	
	/**
	 * 获取成绩分析
	 * @param id
	 * @return
	 */
	public ResultBean getScoreCountLog(String id);
	
	/**
	 * 获取第一次挂科
	 * @param id
	 * @return
	 */
	public ResultBean getFirstDownLog(String id);
	
	
	/**
	 * 保存平均成绩JOB
	 */
	public void saveAvgScoreJob();
	
	/**
	 * 保存总成绩JOB
	 */
	public void saveSumScoreJob();
	
	/**
	 * 保存最高成绩JOB
	 */
	public void saveBestScoreJob();
	
	/**
	 * 保存考试名次JOB
	 * @param schoolYear 学年
	 * @param termCode	学期
	 */
	public void saveScoreRankJob(String schoolYear,String termCode);
	
	/**
	 * 分专业保存最好科目JOB
	 */
	public void saveBestCourseByMajorJob();
	
	/**
	 * 分学生保存最好科目JOB
	 */
	public void saveBestCourseByStuJob();
	
	/**
	 * 保存第一次挂科JOB
	 */
	public void saveFirstDownJob();
	
	/**
	 * 保存在校生平均成绩数据
	 */
	public void saveScoreAvgJob();
	
	/**
	 * 依据学生id获取，各个学期平均成绩走势
	 * 个人曲线+同届本专业平均2条线
	 * @param id
	 * @return
	 */
	public Chart getScore2Line(String id);
	/**
	 * 保存学生综合绩点成绩及其所处排名
	 */
	public void saveGPAScoreJob();
	
	public Page getScoreDetailLog(String id,Page page,List<Condition> conditions);
	
	public List<Map<String, Object>> getScoreDetailGroupBySY(String id);
}
