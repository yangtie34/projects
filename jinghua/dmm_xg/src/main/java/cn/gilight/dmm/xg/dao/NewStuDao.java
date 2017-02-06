package cn.gilight.dmm.xg.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

public interface NewStuDao {
    /**
     * 各部门各个就读学历报到和未报到的新生人数
     * @param schoolYear 当前学年
     * @param deptList 标准权限
     * @return
     */
	public List<Map<String, Object>> queryIsRegisterCount(int schoolYear,List<String> deptList,List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询各就读学历的新生人数
	 * @param schoolYear 当前学年
	 * @param deptList 标准权限
	 * @return
	 */
	public List<Map<String,Object>> queryEdu(int schoolYear,List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 查询学校有几年新生的数据
	 * @param deptList 标准权限
	 * @return
	 */
	public Map<String,Integer> queryMinAndMax(int schoolYear,List<String> deptList);
	/**
	 * 查询某一年的报到人数和报到率
	 * @param schoolYear 学年
	 * @param deptList 标准权限  
 	 * @return
	 */
	public List<Map<String, Object>>  queryCountAndLv(Integer schoolYear,
			List<String> deptList,List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询各单位近五年新生平均报到率
	 * @param schoolYear 当前学年
	 * @param deptList 标准权限
	 * @return
	 */
	public List<Map<String, Object>> queryDeptAvgLv(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询历年新生中的贫困生，助学贷款，学费减免人数
	 * @param schoolYear 当前学年
	 * @param deptList 标准权限
	 * @param table 查询的表名
	 * @return
	 */
	public List<Map<String, Object>> queryCountByAny(int schoolYear,
			List<String> deptList, String table,List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询新生中的贫困生，助学贷款，学费减免的学生的户口类型
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param table 查询的表名
	 * @return
	 */
	public List<Map<String, Object>> queryAnyPie(int schoolYear,
			List<String> deptList, String table,List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询学生sql
	 * @param deptList
	 * @param stuAdvancedList
	 * @return
	 */
	public String getStuSql(List<String> deptList, List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询学生sql
	 * @param schoolYear
	 * @param deptList
	 * @param stuAdvancedList
	 * @return
	 */
	public String queryStuSql(Integer schoolYear, List<String> deptList,
			List<AdvancedParam> stuAdvancedList);
}
