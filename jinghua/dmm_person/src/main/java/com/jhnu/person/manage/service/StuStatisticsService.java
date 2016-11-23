package com.jhnu.person.manage.service;

import java.util.List;
import java.util.Map;

public interface StuStatisticsService {
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
	 * 查询指定单位名称，单位下的学生数，以及单位下的专业数，男生数，女生数(性别比例)
	 * 
	 * @return
	 */
	public  List<Map<String, Object>> queryRs(String pid,String stu_id);
	/**
	 * 查询指定单位下的学生就读各个学历以及人数
	 * 
	 * @return
	 */
	public  List<Map<String, Object>> queryXl(String pid);
	/**
	 * 查询指定单位下的学生的生源地分布(生源地分布)
	 * 
	 * @return
	 */
	public  List<Map<String, Object>> querySyd(String pid,String stu_id);
	/**
	 * 查询指定单位下的学生的年龄分布(年龄分布)
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryNl(String pid,String stu_id);
	/**
	 * 查询指定单位下各学历类别的学生数(学历组成)
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryRydb(String pid);
	/**
	 * 查询指定单位下各政治面貌各学历类别的学生数
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryZzmm(String pid,String stu_id);
	/**
	 * 查询指定单位下各民族的学生数
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryMz(String pid,String stu_id);
	/**
	 * 查询指定单位下各汉族的学生数和少数民族的学生数(民族组成)
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryMzCount(String pid,String stu_id);
	/**
	 * 查询指定单位下各政治面貌各学历类别的学生数(政治面貌)
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryZzmm1(String pid,String stu_id);
}
