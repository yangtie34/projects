package cn.gilight.product.bookRke.dao;

import java.util.List;
import java.util.Map;

public interface LibraryRkeDao {
	/**
	 * 获取出入总次数
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @param groups
	 * @return
	 */
	public List<Map<String, Object>> getCounts(String startDate,
			String endDate, Map<String, String> deptTeach, String lb);
	/**
	 * 获取人均出入次数
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @param groups
	 * @return
	 */
	public List<Map<String, Object>> getAvgCounts(String startDate,
			String endDate, Map<String, String> deptTeach, String lb);
	/**
	 * 获取入馆率
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @param groups
	 * @return
	 */
	public List<Map<String, Object>> getInRate(String startDate,
			String endDate, Map<String, String> deptTeach, String lb);
	/**
	 * 获取出入频次
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @param groups
	 * @return
	 */
	public List<Map<String, Object>> getCountsByGroup(String startDate,
			String endDate, Map<String, String> deptTeach, String lb, String fieldlb);
	/**
	 * 获取入馆次数区间分布
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @param groups
	 * @return
	 */
	public List<Map<String, Object>> getQjByGroup(String startDate,
			String endDate, Map<String, String> deptTeach, String lb);
	/**
	 * 分学院学生出入对比分析
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 */
	public List<Map<String, Object>> getCountsByDept(String startDate,
			String endDate, Map<String, String> deptTeach);
	/**
	 * 分学年学生出入对比
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 */
	public List<Map<String, Object>> getCountsForYears( Map<String, String> deptTeach);
	public List<Map<String, Object>> getStuRkeTrend(
			Map<String, String> deptTeach, String flag, String flagCode);
}
