package cn.gilight.dmm.xg.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

public interface FeeRemissionDao {

	/**
	 * 得到助学减免的学年
	 * @return
	 */
	List<Integer> querySchoolYear();
	/**
	 * 得到总的学费减免人数以及金额
	 * @param schoolYear 学年
	 * @param id   学生类别代码(本科生，专科生等等)
	 * @return     List<Map<String, Object>>
	 */
	List<Map<String, Object>> queryJmInfo(List<String> deptList,String schoolYear,String edu,List<AdvancedParam> advancedList);
	/**
	 * 学费减免分布(班级)
	 * @param schoolYear 学年
	 * @param id   学生类别代码(本科生,专科生等等)
	 * @return     List<Map<String, Object>>
	 */
	List<Map<String, Object>> queryBjjmfb(int schoolYear,List<String> deptList,List<AdvancedParam> advancedList,String fb);
	/**
	 * 学费减免分布
	 * @param schoolYear 学年
	 * @param id   学生类别代码(本科生,专科生等等)
	 * @return     List<Map<String, Object>>
	 */
	String queryJmfb(List<String> deptList,List<AdvancedParam> advancedList,int year);
	/**
	 * 查询历年学费减免变化
	 * @param schoolYear
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> queryYearJmfb(List<String> deptList,int year,String bh,List<AdvancedParam> advancedList, String edu);
	/**
	 * 得到学费减免类型
	 * @return
	 */
	List<Map<String, Object>> queryJmType();
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
    String getXfjmStuSql(List<String> deptList,List<AdvancedParam> advancedList,int year);
	
}
