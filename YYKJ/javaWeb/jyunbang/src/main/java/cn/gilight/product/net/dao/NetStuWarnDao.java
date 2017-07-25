package cn.gilight.product.net.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

/**
 * 学生上网预警
 *
 */
public interface NetStuWarnDao {
	
	/**
	 * 获取预警人员
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @param type 获取类型，time,flow
	 * @param value 约束值
	 * @return
	 */
	public Page getNetWarnStus(int currentPage,int numPerPage,int totalRow,String startDate,String endDate,Map<String,String> deptTeach,String type,String value);
	
	/**
	 * 预警人员分类型展示
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @param type 获取类型，time,flow
	 * @param value 约束值
	 * @param codeType 类型 xb,xl,mz
	 * @return
	 */
	public List<Map<String, Object>> getNetStuType(String startDate,String endDate,Map<String,String> deptTeach,String type,String value,String codeType);
	
	/**
	 * 按类型获取预警人员信息
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @param type 获取类型，time,flow
	 * @param value 约束值
	 * @param codeType 类型 xb,xl,mz
	 * @param codeValue code值
	 * @return
	 */
	public Page getNetWarnTypeStus(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,String startDate,String endDate,Map<String,String> deptTeach,String type,String value,String codeType,String codeValue);
	
	
}
