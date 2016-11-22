package com.jhkj.mosdc.sc.service;

/**
 * 高产作者service
 * 
 * @author baihy
 * 
 */
public interface ProductiveAuthorService {

	/**
	 * 通过年份获取高产作者的人数和增长人数
	 * 
	 * @param params
	 * @return
	 */
	public String getTotalAndChangeByYear(String params);

	/**
	 * 通过年份获取高产作者的学位
	 * 
	 * @param params
	 * @return
	 */
	public String getXwByYear(String params);

	/**
	 * 通过年份获取高产作者的学历
	 * 
	 * @param params
	 * @return
	 */
	public String getXlByYear(String params);

	/**
	 * 通过年份获取高产作者的职称
	 * 
	 * @param params
	 * @return
	 */
	public String getZcByYear(String params);

	/**
	 * 通过年份获取高产作者的来校时间
	 * 
	 * @param params
	 * @return
	 */
	public String getLxsjByYear(String params);

	/**
	 * 通过年份获取高产作者的培养类型
	 * 
	 * @param params
	 * @return
	 */
	public String getPylxByYear(String params);

	/**
	 * 通过年份获取高产作者的学院
	 * 
	 * @param params
	 * @return
	 */
	public String getXyByYear(String params);

	/**
	 * 年龄分布
	 * 
	 * @param params
	 * @return
	 */
	public String getAgeScatterByYear(String params);

	/**
	 * @param params
	 * @return
	 */
	public String getLngczzsbhjsByYear(String params);

	/**
	 * @param params
	 * @return
	 */
	public String getGdwdbByType(String params);

	/**
	 * @param params
	 * @return
	 */
	public String getLnqsByType(String params);

	/**
	 * @param params
	 * @return
	 */
	public String getTableXueWeiPage(String params);

	/**
	 * @param params
	 * @return
	 */
	public String getTableLngczzPage(String params);

	/**
	 * @param params
	 * @return
	 */
	public String getTableLwSexPage(String params);

	/**
	 * @param params
	 * @return
	 */
	public String getTable2(String params);

}
