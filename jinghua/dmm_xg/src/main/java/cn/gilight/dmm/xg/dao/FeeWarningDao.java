package cn.gilight.dmm.xg.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

/**
 * 学费预警
 * @author lijun
 *
 */
public interface FeeWarningDao {

	/**
	 * 得到当前学年未缴纳学费学生总人数和总金额
	 * @param deptList
	 * @return
	 */
	Map<String, Object> queryXfInfo(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 未缴纳学费类型分布
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryXflxfb(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 未缴纳学费学生类型分布
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryXslxfb(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 某一学年欠费金额及比例(欠费人数/人数)
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryQfjeAndRadio(int year,List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 欠费详细信息
	 * @param deptList
	 * @return
	 */
	String queryQfInfo(List<String> deptList,List<AdvancedParam> advancedList,Integer year,String edu,String lx,String slx);
	
	/**
	 *发送邮件
	 * @return
	 */
	Map<String, Object> sendQfInfo(List<String> deptList,List<AdvancedParam> advancedList,String year,String edu,String lx,String slx);
	/**
	 * 得到学籍状态类型
	 * @return
	 */
	List<Map<String, Object>> queryXjList();
	/**
	 * 得到欠费类型
	 * @return
	 */
	List<Map<String, Object>> queryQfTypeList();
	/**
	 * 得到学院email地址
	 * @return
	 */
	List<Map<String, Object>> queryEmailList();
	/**
	 * 得到学生详细信息表
	 * @param deptList
	 * @param advancedList
	 * @return
	 */
	String getDetailstuSql(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 得到欠费学生详细信息表
	 * @param deptList
	 * @param advancedList
	 * @return
	 */
    String getNodegreeStuSql(List<String> deptList,List<AdvancedParam> advancedList);
}
