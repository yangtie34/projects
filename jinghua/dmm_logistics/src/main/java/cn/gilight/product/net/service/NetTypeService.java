package cn.gilight.product.net.service;

import java.util.List;
import java.util.Map;

/**
 * 上网类型分析
 *
 */
public interface NetTypeService {
	
	/**
	 * 获取上网信息
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @param type 获取类型，all,onType
	 * @return
	 */
	public List<Map<String, Object>> getCounts(String startDate,String endDate,Map<String,String> deptTeach,String type);
	
	/**
	 * 获取上网学生占比
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @param type 获取类型，all,onType
	 * @return
	 */
	public List<Map<String, Object>> getNetStuRatio(String startDate,String endDate,Map<String,String> deptTeach,String type);
	
	/**
	 * 获取上网信息趋势
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @param type 获取类型，all,onType
	 * @return
	 */
	public List<Map<String, Object>> getCountsTrend(Map<String,String> deptTeach,String type);
	
	/**
	 * 获取上网学生占比趋势
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @param type 获取类型，all,onType
	 * @return
	 */
	public List<Map<String, Object>> getNetStuRatioTrend(Map<String,String> deptTeach,String type);
	
	/**
	 * 分时段获取上网学生人数
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @param type 获取类型，all,onType
	 * @return
	 */
	public List<Map<String, Object>> getNetHourStu(String startDate,String endDate,Map<String,String> deptTeach,String type);
	
	
}
