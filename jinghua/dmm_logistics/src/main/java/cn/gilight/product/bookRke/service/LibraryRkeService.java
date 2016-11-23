package cn.gilight.product.bookRke.service;

import java.util.List;
import java.util.Map;

public interface LibraryRkeService {
	/**
	 * 出入门禁信息
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 */
	public Map<String, List<Map<String, Object>>> getRkeInfo(String startDate,String endDate,Map<String,String> deptTeach,String lb);
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
	/**
	 * 分类型按月统计趋势
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 */
	public List<Map<String, Object>> getStuRkeTrend( Map<String, String> deptTeach,String flag,String flagCode);
	
}
