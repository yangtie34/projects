package cn.gilight.dmm.teaching.service;


import java.util.Map;


/**
 * 经费分析
 * @author caijc
 * 
 */
public interface TeachingFundsService {
	
	/**
	 * 传入年学校经费总额以及生均
	 */
	public Map<String, Object> getTeaFundsByAll(int year);
	
	/**
	 * 查询当年教学经费金额(饼图)
	 */
	public Map<String, Object> getTeaFundsBySty(int year);

	/**
	 * 查询近五年学校经费趋势
	 */
	public Map<String, Object> getTeaFundsLine(int year);

	/**
	 * 查询历年经费 （按比例）
	 */
	public Map<String, Object> getTeaFundsByZB(int year);

	/**
	 * 查询历年经费 （按金额）
	 */
	public Map<String, Object> getTeaFundsByCount(int year);

	/**
	 * 生均对比
	 */
	public Map<String, Object> getTeaFundsAvg(int year);

	/**
	 * 各学院经费一览
	 */
	public Map<String, Object> getTeaFundsByDept(int year);
	
	/**
	 * 获得经费所有年份
	 */
	public Map<String, Object> getTeaFundsYear();
}
