package com.jhnu.person.manage.dao;

import java.util.List;
import java.util.Map;

public interface StuStatisticsDao {
	/**
	 * 查询组织机构数数据
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getSchoolName(String pid);
	/**
	 * 查询指定单位名称，单位下的学生数，以及单位下的专业数，男生数，女生数(性别比例)
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getRs(String Id,String stu_id);
	/**
	 * 查询指定单位下的学生就读各个学历以及人数
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getXl(String Id);
	public List<Map<String, Object>> getYxdName(String pid);
	/**
	 * 查询指定单位下的学生的生源地分布
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getSyd(String Id,String stu_id);
	/**
	 * 查询指定单位下的学生的年龄分布
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getNl(String Id,String stu_id);
	/**
	 * 查询指定单位下各学历类别的学生数
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getRydb(String Id);
	/**
	 * 查询指定单位下各政治面貌各学历类别的学生数
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getZzmm(String Id,String stu_id);
	/**
	 * 查询指定单位下各民族的学生数
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMz(String Id,String stu_id);
	/**
	 * 查询指定单位下各汉族的学生数和少数民族的学生数
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMzCount(String Id,String stu_id);
	/**
	 * 查询指定单位下各政治面貌各学历类别的学生数
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getZzmm1(String Id,String stu_id);
}
