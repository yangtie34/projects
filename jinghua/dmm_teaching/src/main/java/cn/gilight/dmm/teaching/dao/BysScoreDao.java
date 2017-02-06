package cn.gilight.dmm.teaching.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

public interface BysScoreDao {

	/**
	 * 查询课程list
	 * @return
	 */
	public List<Map<String, Object>> queryCourse();
    /**
     * 查询学生在校期间个学期成绩sql
     * @param deptList 标准权限
     * @param stuAdvancedList 高级查询参数
     * @param xz 学制
     * @param period 届别
     * @param scoreType 成绩类型
     * @param target 成绩指标
     * @return List<Map<String,Object>>
     */
	public List<Map<String, Object>> queryStuScoreList(List<String> deptList,
			List<AdvancedParam> stuAdvancedList, int xz, int period,String scoreType,String target);
	/**
	 * 查询某届学生成绩分布
	 * @param stuAdvancedList 高级查询参数
	 * @param deptList 标准权限
	 * @param period 届别
	 * @param xz 学制
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryScoreFb(List<AdvancedParam> stuAdvancedList,
			List<String> deptList, int period, int xz);
	/**
	 * 等级制对照sql
	 * @return String
	 */
	public String getCaseWhenSql();

}
