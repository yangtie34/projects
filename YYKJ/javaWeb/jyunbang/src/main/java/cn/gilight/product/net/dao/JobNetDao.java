package cn.gilight.product.net.dao;

import java.util.Map;

public interface JobNetDao {
	/**
	 * 学生网络信息log
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,Integer> updateNetStuMonth(String yearMonth);
	/**
	 * 网络信息log
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,Integer> updateNetTypeMonth(String yearMonth);
	
	/**
	 * 学生人数LOG
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,Integer> updateStuNumMonth(String yearMonth);
	
	/**
	 * 网络信息趋势log
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,Integer> updateNetTrend();
	/**
	 * 教师网络账号预警
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,Integer> updateNetTeaWarn(String yearMonth);
	
	/**
	 * 教师上网月度LOG
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,Integer> updateNetTeaMonth(String yearMonth);
}
