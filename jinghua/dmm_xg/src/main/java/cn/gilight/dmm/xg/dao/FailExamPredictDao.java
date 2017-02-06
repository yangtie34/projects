package cn.gilight.dmm.xg.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

/**
 * 挂科预测
 * @author lijun
 *
 */
public interface FailExamPredictDao {

	/**
	 * 得到当前学年期末考试人数及挂科信息
	 * @param deptList
	 * @return
	 */
	Map<String, Object> queryGkInfo(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 挂科预测学生性别类型分布
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryGklxfb(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 挂科预测学生类型分布
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryXslxfb(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 某一学年挂科预测学生人数和挂科概率(预测挂科人数/挂科概率)
	 * @param deptList
	 * @return
	 */
	String queryGkrsAndRadio(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 挂科预测学生的详细信息
	 * @param deptList
	 * @return
	 */
	String queryGkxxInfo(List<String> deptList,List<AdvancedParam> advancedList);
	
	/**
	 *发送邮件
	 * @return
	 */
	Map<String, Object> sendGkInfo(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 得到学籍状态类型
	 * @return
	 */
	List<Map<String, Object>> queryXjList();
	/**
	 * 得到挂科类型
	 * @return
	 */
	List<Map<String, Object>> queryGkTypeList();
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
	 * 得到无学位学生详细信息表
	 * @param deptList
	 * @param advancedList
	 * @return
	 */
    String getNodegreeStuSql(List<String> deptList,List<AdvancedParam> advancedList);
}
