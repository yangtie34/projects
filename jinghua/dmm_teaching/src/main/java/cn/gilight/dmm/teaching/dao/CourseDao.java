package cn.gilight.dmm.teaching.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

public interface CourseDao {
	
	/**
	 * 获取课程属性分布信息
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return list
	 */
	public List<Map<String , Object>> queryCourseByAttribute(List<String> deptList,List<AdvancedParam> advancedParamList, String edu,String schoolYear, String termCode);
	
	/**
	 * 获取课程性质分布信息
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return list
	 */
	public List<Map<String , Object>> queryCourseByNature(List<String> deptList,List<AdvancedParam> advancedParamList,String edu, String schoolYear, String termCode);
	
	/**
	 * 获取课程类别分布信息
	 * @param deptList 标准权限
	 * @param advancedParamList 高级查询参数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return list 
	 */
	public List<Map<String , Object>> queryCourseByCategory(List<String> deptList,List<AdvancedParam> advancedParamList, String edu,String schoolYear, String termCode);
	
	/**
	 * 下拉选选项
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return
	 */
	public List<Map<String,Object>> queryselectDown();
	/**
	 * 所有课程班级，专业，学院 数据sql
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param codeType 课程（属性，性质，类别）
	 * @param code 课程（属性，性质，类别）编码
	 * @return
	 */
	public String getlevelSql(List<String> deptList,String edu,String schoolYear, String termCode,String codeType,String code);
	/**
	 * 所有学科课程总数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param codeType课程（属性，性质，类别）
	 * @param code 课程（属性，性质，类别）编码
	 * @return
	 */
	public String getCourseBySubject(List<String> deptList,String edu,String schoolYear, String termCode,String codeType,String code,String id);
	
	/**
	 * 课程总门数
	 * @param deptList 标准权限
	 * @param edu 学历
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return int
	 */
	public int queryAbstract(List<String> deptList,String edu,String schoolYear, String termCode);
}
