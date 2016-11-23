package com.jhnu.person.manage.service;

import java.util.List;
import java.util.Map;

public interface TeaStatisticsService {
	/**
	 * 查询组织机构数数据
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getSchoolName();
	/**
	 * 查询指定单位名称，男生数，女生数(性别比例)
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getRs(String pid);
	/**
	 * 查询指定单位下，教师的状态(在编,非在编等等)
	 * @param Id
	 * @return
	 */
	public List<Map<String,Object>>  getJszt(String pid);
	/**
	 * 查询指定单位下各汉族的学生数和少数民族的学生数
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMzCount(String Id);
	/**
	 * 查询指定单位下的学生的年龄分布(年龄分布)
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getNl(String pid);
	/**
	 * 查询指定单位下各民族的学生数(民族组成)
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMz(String pid);
	/**
	 * 查询指定单位下各政治面貌(政治面貌)
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryZzmm1(String pid);
/**
	 * 查询指定单位下各政治面貌各学历类别的学生数(政治面貌)
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getZzmm(String pid);
	/**
	 * 查询指定单位下的教师的来源地分布(来源地统计)
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getLyd(String pid);
	/**
	 * 查询指定单位下的学生就读各个学历以及人数(学历组成)
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getXl(String pid);
	/**
	 * 查询指定单位下职称级别统计(职称级别统计)
	 * @param pid
	 * @return
	 */
	public List<Map<String, Object>> getZcjb(String pid);
	/**
	 * 查询指定单位下职称级别统计(职称统计)
	 * @param pid
	 * @return
	 */
	public List<Map<String, Object>> getZc(String pid);
}
