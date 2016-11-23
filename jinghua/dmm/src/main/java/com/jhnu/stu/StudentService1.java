package com.jhnu.stu;

import java.util.List;
import java.util.Map;

public interface StudentService1 {
	/**
	 * 查询组织机构数数据
	 * 
	 * @return
	 */
	public List<Map<String, Object>> querySchoolName(String pid);
	/**
	 * 查询指定单位名称
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryYxdName(String pid);
	/**
	 * 查询指定单位名称，单位下的学生数，以及单位下的专业数，男生数，女生数
	 * 
	 * @return
	 */
	public  List<Map<String, Object>> queryRs(String pid);
	/**
	 * 查询指定单位下的学生就读各个学历以及人数
	 * 
	 * @return
	 */
	public  List<Map<String, Object>> queryXl(String pid);
	/**
	 * 查询指定单位下的学生的生源地分布
	 * 
	 * @return
	 */
	public  List<Map<String, Object>> querySyd(String pid);
	/**
	 * 查询指定单位下的学生的年龄分布
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryNl(String pid);
	/**
	 * 查询指定单位下各学历类别的学生数
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryRydb(String pid);
	/**
	 * 查询指定单位下各政治面貌各学历类别的学生数
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryZzmm(String pid);
	/**
	 * 查询指定单位下各民族的学生数
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryMz(String pid);
	/**
	 * 查询指定单位下各汉族的学生数和少数民族的学生数
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryMzCount(String pid);
	/**
	 * 查询指定单位下各政治面貌各学历类别的学生数
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryZzmm1(String pid);
}
