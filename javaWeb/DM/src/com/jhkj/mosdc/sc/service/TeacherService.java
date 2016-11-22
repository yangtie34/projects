package com.jhkj.mosdc.sc.service;

public interface TeacherService {
	
	/**
	 * 根据统计类别获得对应的教职工数。
	 * @param json
	 * @return
	 */
	public String getCountsByTjlb(String params);
	
	/**
	 * 根据教职工类别获取教职工的性别分布
	 * @param json
	 * @return
	 */
	public String getXbfbByTjlb(String params);
	/**
	 * 根据教职工类别获取教职工的年龄段分布
	 * @param json
	 * @return
	 */
	public String getNldfbByTjlb(String params);
	/**
	 * 根据教职工类别获取教职工的民族组成
	 * @param json
	 * @return
	 */
	public String getMzzcByTjlb(String params);
	/**
	 * 根据教职工类别获取教职工的政治面貌
	 * @param json
	 * @return
	 */
	public String getZzmmByTjlb(String params);
	/**
	 * 根据教职工类别获取教职工的生源地
	 * @param json
	 * @return
	 */
	public String getLydByTjlb(String params);
	/**
	 * 根据教职工类别获取教职工的学科分布
	 * @param json
	 * @return
	 */
	public String getRyxkByTjlb(String params);
	/**
	 * 根据教职工类别获取教职工的学位组成
	 * @param json
	 * @return
	 */
	public String getXwzcByTjlb(String params);
	/**
	 * 根据教职工类别获取教职工的学历组成
	 * @param json
	 * @return
	 */
	public String getXlzcByTjlb(String params);
	/**
	 * 获取饼状图的学科分类组成数据
	 * @param params
	 * @return
	 */
	public String getRyxkByTjlb2(String params);
}
