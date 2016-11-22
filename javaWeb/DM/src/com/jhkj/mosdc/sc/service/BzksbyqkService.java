package com.jhkj.mosdc.sc.service;

/**   
* @Description: TODO 本专科生毕业情况统计
* @author Sunwg  
* @date 2014-8-26 下午3:43:45   
*/
public interface BzksbyqkService {
	/** 
	* @Title: queryYbyqk  
	* @Description: TODO 应毕业情况 查询
	* @return String
	*/
	public String queryYbyqk(String json);
	
	/** 
	* @Title: querySjbyqk 
	* @Description: TODO 实际毕业情况查询
	* @return String
	*/
	public String querySjbyqk(String json);
	
	/** 
	* @Title: queryXzkyqk 
	* @Description: TODO 选择考研学生情况
	* @return String
	*/
	public String queryXzkyqk(String json);
	
	
}
