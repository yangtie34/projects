package cn.gilight.business.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.business.model.TCode;

/**
 * 业务Dao
 * 
 * @author xuebl
 * @date 2016年4月28日 下午6:58:23
 */
public interface BusinessDao {

	/**
	 * 查询学校数据
	 */
	public Map<String, Object> querySchoolData();
	
	/**
	 * 查询标准代码
	 * @param codeType 代码类型
	 * @param codes 代码 （null查询所有）
	 * @return List<TCode>
	 */
	public List<TCode> queryBzdmList(String codeType, String codes);


	/**
	 * 对学生sql数据进行 年级分组
	 * @param stuSql 学生sql（select stu_id from table）
	 * @param schoolYear 学年（2015）
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryGradeGroup(String stuSql, int schoolYear);
	
	/**
	 * 查询有用的学科门类
	 * @return List<Map<String,Object>>
	 * <br> [ {name:'',id:''}, {} ]
	 */
	public List<Map<String, Object>> querySubjectUsefulList();

	/**
	 * 查询一张表中的最小学年数据
	 * @param table
	 * @param column
	 * @return Integer
	 */
	public Integer queryMinSchoolYear(String table, String column);
	
	
	
	/** 
	* @Description: 查询学科门类代码
	* @Return: List<Map<String,Object>>
	*/
	public List<Map<String, Object>> queryCodeSubject();

}