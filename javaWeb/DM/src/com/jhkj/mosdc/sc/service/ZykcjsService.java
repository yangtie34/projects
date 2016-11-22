package com.jhkj.mosdc.sc.service;

/**   
* @Description: TODO 专业课程建设Service
* @author Sunwg  
* @date 2014-8-7 下午5:11:08   
*/
public interface ZykcjsService {
	/** 
	* @Title: queryZylbs 
	* @Description: TODO 查询专业类别数量
	* @return String
	*/
	public String queryZylbs(String params);
	
	/** 
	* @Title: queryZys 
	* @Description: TODO 查询专业数量
	* @return String
	*/
	public String queryZys(String params);
	
	/** 
	* @Title: queryKcs 
	* @Description: TODO 查询课程数
	* @return String
	*/
	public String queryKcs(String params);
	
	/** 
	* @Title: queryJcs 
	* @Description: TODO 查询教材数量
	* @return String
	*/
	public String queryJcs(String params);
	
	/** 
	* @Title: queryZyflsl  
	* @Description: TODO 查询专业分类数量
	* @return String 
	*/
	public String queryZyflsl(String params);
	
	/** 
	* @Title: queryZyflxx 
	* @Description: TODO 查询专业分类详细信息
	* @return String
	*/
	public String queryZyflxx(String params);
}
