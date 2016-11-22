package com.jhkj.mosdc.sc.service;

/**   
* @Description: TODO 本专科生毕业情况统计
* @author Sunwg  
* @date 2014-8-26 下午3:43:45   
*/
public interface BzkszsxxService {
	/** 
	* @Title: queryYxzsqk 
	* @Description: TODO 各院系招生情况
	* @return String
	*/
	public String queryYxzsqk(String json);
	
	/** 
	* @Title: queryFsdzsqk 
	* @Description: TODO 招生各分数段学生数
	* @return String
	*/
	public String queryFsdzsqk(String json);
	
	/** 
	* @Title: queryYearszs 
	* @Description: TODO 查询历年招生数
	* @return String
	*/
	public String queryYearszs(String json);
	
	/** 
	* @Title: queryLngkfs 
	* @Description: TODO 查询历年高考分数变化
	* @return String
	*/
	public String queryLngkfs(String json);
}
