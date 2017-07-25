package cn.gilight.product.net.service;

import java.util.List;
import java.util.Map;

/**
 * 学生分学院上网分析
 *
 */
public interface NetStuDeptService {
	
	/**
	 * 获取上网人数
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 */
	public List<Map<String, Object>> getNetStus(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 获取上网信息
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @param type 类型 all,one
	 * @return
	 */
	public List<Map<String, Object>> getNetCounts(String startDate,String endDate,Map<String,String> deptTeach,String type);
	
	/**
	 * 获取上网信息
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @param type 类型 on,out,in
	 * @return
	 */
	public List<Map<String, Object>> getNetTimes(String startDate,String endDate,Map<String,String> deptTeach,String type);
	
	/**
	 * 获取预警人数对比
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @param type 类型 time,flow
	 * @return
	 */
	public List<Map<String, Object>> getNetWarnStus(String startDate,String endDate,Map<String,String> deptTeach,String type,String value);
	
}
