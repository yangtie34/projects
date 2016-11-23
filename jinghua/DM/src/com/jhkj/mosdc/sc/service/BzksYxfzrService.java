package com.jhkj.mosdc.sc.service;

/**   
* @Description: TODO 院系负责人
* @author Sunwg  
* @date 2014-10-27 下午3:58:46   
*/
public interface BzksYxfzrService {
	/** 
	* @Title: queryYxfzrList 
	* @Description: TODO 查询院系负责人列表
	* @return String
	*/
	public String queryYxfzrList(String params);
	
	/** 
	* @Title: updateYxfzr 
	* @Description: TODO 更新院系负责人
	* @return String
	*/
	public String updateYxfzr(String params);
	
	/** 
	* @Title: queryJzglist 
	* @Description: TODO 查询教职工信息
	* @return String
	*/
	public String queryJzglist(String params);
}
