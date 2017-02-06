package cn.gilight.dmm.teaching.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

public interface BysDegreeDao {

	/**
	 * 根据学生sql，学年查询出成绩集合
	 * @param stuSql 学生sql
	 * @param type 成绩类型
	 * @param schoolYear 学年
	 * @return List<Double>
	 */
	public List<Double> getValueByList(String stuSql, String type, String schoolYear);
    /**
     * 根据学生sql,成绩类型获取分组数据
     * @param stuSql 学生sql
     * @param type 成绩类型
     * @return List<Map<String, Object>>
     */
	public List<Map<String, Object>> getGpaGroup(String stuSql,String type);
	/**
	 * 根据部门sql获取表格结果
	 * @param schoolYear 学年
	 * @param deptSql 部门sql
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getListByDeptList(String schoolYear,
			String deptSql);
	/**
	 * 根据学科sql获取表格结果
	 * @param schoolYear 学年
	 * @param deptSql 学科sql
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getListBySubjectList(String schoolYear,
			String subjectSql);
	/**
	 * 获取各部门毕业率和学位授予率
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @return List<Map<String, Object>> 
	 */
	public List<Map<String, Object>> getLvByDept(String schoolYear,
			List<String> deptList, List<AdvancedParam> stuAdvancedList);

}
