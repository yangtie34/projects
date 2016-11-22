package com.jhkj.mosdc.sc.job;
/**
 * 计算成绩与图书馆出入关系
 * @author Administrator
 *
 */
public interface DemoAnalysisService {
	/**
	 * 根据学年、学号计算各学生的专业成绩排名
	 * @return
	 */
	public boolean saveCjpm2Temp();
	/**
	 * 计算每个学生的学年出入图书馆次数
	 * @return
	 */
	public boolean saveTsgMjcs2Temp();
	/**
	 * 计算吃早餐的次数。
	 * @return
	 */
	public boolean saveZccs2Temp();
	
	public boolean updatePm();
	
	public String queryChartDate(String params);
	/**
	 * 获取成绩与图书馆进出次数的相关性分析展示数据。
	 * @param params
	 * @return
	 */
	public String queryChartDate2(String params);
	/**
	 * 获取成绩与吃早餐的次数的相关性分析展示数据
	 * @param params
	 * @return
	 */
	public String queryChartDate22(String params);
	/**
	 * 获取染色点显示数据。
	 * @param params
	 * @return
	 */
	public String queryChartData3(String params);
}
