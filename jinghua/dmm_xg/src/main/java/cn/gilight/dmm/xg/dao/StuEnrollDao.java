package cn.gilight.dmm.xg.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

public interface StuEnrollDao {
	/**
	 * 查询当前部门下在籍学生数
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询条件
	 * @param Id 部门id
	 * @return Map<String,Object>
	 */
	public	Map<String, Object> queryStuCountByDept(int schoolYear,List<String> deptList,String Id,Boolean bs,List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询当前部门在各校区的学生数
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询条件
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryStuCountIncampus(int schoolYear,List<String> deptList,List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询当前部门各个就读学历的学生数
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询条件
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryStuCountByEdu(int schoolYear,List<String> deptList,List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询当前部门各个年级的学生数
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询条件
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryStuCountByGrade(int schoolYear,List<String> deptList,List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询当前部门下的男生数和女生数
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询条件
	 * @param bs 表示是否加上学籍状态
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryStuCountBySex(int schoolYear,List<String> deptList,Boolean bs,List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询当前部门下各年龄段的学生数1
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询条件
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryStuCountByAge(int schoolYear,List<String> deptList,List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询当前部门下各民族的学生数
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询条件
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryStuCountByNation(int schoolYear,List<String> deptList,List<AdvancedParam> stuAdvancedList,Boolean isOld);
	/**
	 * 查询当前部门下各学生状态的学生数
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询条件
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryStuCountByState(int schoolYear,List<String> deptList,List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询当前部门下各学习形式的学生数
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询条件
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryStuCountByForm(int schoolYear,List<String> deptList,List<AdvancedParam> stuAdvancedList,Boolean isOld);
	/**
	 * 查询当前部门下各学习方式的学生数
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询条件
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryStuCountByStyle(int schoolYear,List<String> deptList,List<AdvancedParam> stuAdvancedList,Boolean isOld);
	/**
	 * 查询当前部门下各生源地的学生数
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询条件
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryStuFrom(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询当前部门下级节点的学生数
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询条件
	 * @param id 部门id
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryContrastByDept(int schoolYear,List<String> deptList,List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询当前部门下各政治面貌的学生数
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询条件
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryContrastByPolitics(int schoolYear,List<String> deptList, List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询政治面貌编码表
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryPolitics();
	/**
	 * 查询本省以及外省历年学生人数变化趋势
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询条件
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryStuCountByBen(int schoolYear,List<String> deptList,List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询某一学年学生入学时的平均年龄
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询条件
	 * @return Map<String, Object>
	 */
	public List<String> queryStuAvgAge(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询某一学年各个政治面貌的学生人数
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询条件
	 * @return Map<String, Object>
	 */
	public List<Map<String,Object>> queryPoliticsLine(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询团员和非团员的人数
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询条件
	 * @return Map<String, Object>
	 */
	public List<Map<String, Object>> queryPoliticsCount(int schoolYear,List<String> deptList,List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询代码
	 * @return Map<String, Object>
	 */
	public Map<String, Object> queryBzdm();
	/**
	 * 查询表数据
	 * @param table
	 * @param result
	 * @param column
	 * @param name
	 * @return
	 */
	public String queryTableData(String table, String result, String column,
			String name);
	/**
	 * 查询研究生各学生方式的人数
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryYjsCountByStyle(String stuSql,int schoolYear,
			List<String> deptList, List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询研究生各录取类别人数
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @return List<Map<String, Object>> 
	 */
	public List<Map<String, Object>> queryYjsCountByDxjy(String stuSql,int schoolYear,
			List<String> deptList, List<AdvancedParam> stuAdvancedList);
	/**
	 * 研究生各年级人数
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @return List<Map<String, Object>> 
	 */
	public List<Map<String, Object>> queryYjsGrade(String stuSql,int schoolYear,
			List<String> deptList, List<AdvancedParam> stuAdvancedList);
	/**
	 * 博士生各年级人数
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @return List<Map<String, Object>> 
	 */
	public List<Map<String, Object>> queryBsGrade(String stuSql,int schoolYear,
			List<String> deptList, List<AdvancedParam> stuAdvancedList);
	/**
	 * 获取最大学制
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @return int
	 */
	public int getMaxXz(int schoolYear, List<String> deptList,
			List<AdvancedParam> stuAdvancedList);
}
