package cn.gilight.dmm.teaching.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年7月12日 上午11:33:00
 */
public interface TeacherGroupDao {

	
	/**
	 * 教职工 高级人才分组
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> querySeniorGroup(String teaSql);

	/**
	 * 学生 培养层次分组
	 * @param stuSql
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryStuTrainingGroup(String stuSql);

	/**
	 * 教职工 职务等级分组
	 * @param teaSql
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryTechnicalGroup(String teaSql);
	/**
	 * 职务等级（查所有级别）
	 * @param teaSql
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryTechnicalGroupRightJoin(String teaSql);
	
	/**
	 * 教职工 学位分组
	 * @param teaSql
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryDegreeGroup(String teaSql);
	/**
	 * 教职工 学位分组（查所有级别）
	 * @param teaSql
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryDegreeGroupRightJoin(String teaSql);
	
	/**
	 * 教职工 学历分组
	 * @param teaSql
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryEduGroup(String teaSql);
	/**
	 * 教职工 学历分组（查所有级别）
	 * @param teaSql
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryEduGroupRightJoin(String teaSql);

	/**
	 * 学科师资分布
	 * @param teaSql
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> querySubjectGroup(String teaSql);
	
}