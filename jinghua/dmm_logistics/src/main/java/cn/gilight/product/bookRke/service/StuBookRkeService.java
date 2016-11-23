package cn.gilight.product.bookRke.service;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

public interface StuBookRkeService {
	/**
	 * 学生出入图书馆活跃排名top100
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 */
	public Page getRankLively(int currentPage,int numPerPage,int totalRow,String startDate,
			String endDate, Map<String, String> deptTeach);
	/**
	 * 按类别统计学生出入图书馆人数比例
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @param lb
	 * @return
	 */
	public Map<String, List<Map<String, Object>>> getBlByLB(String startDate,
			String endDate, Map<String, String> tTeach);
	
	/**
	 * 分学院学生活跃出入对比分析
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 */
	public List<Map<String, Object>> getCountsByDeptLively(String startDate,
			String endDate, Map<String, String> deptTeach);
	/**
	 * 分学院学生非活跃出入对比分析
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 */
	public List<Map<String, Object>> getCountsByDeptNoLively(String startDate,
			String endDate, Map<String, String> deptTeach);
	
	
	/**
	 * 用户按月统计趋势
	 * @param stuId
	 * @return
	 */
	public List<Map<String, Object>> getStuRkeTrend(String stuId);
	
}
