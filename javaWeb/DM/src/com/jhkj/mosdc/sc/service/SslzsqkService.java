package com.jhkj.mosdc.sc.service;

/**   
* @Description: TODO 宿舍楼住宿情况
* @author Sunwg  
* @date 2014-9-18 上午11:25:38   
*/
public interface SslzsqkService {
	/** 
	* @Description: TODO 查询所有宿舍楼情况
	* @return String
	*/
	public String queryAllDorms(String params);
	
	/** 
	* @Description: TODO 查询宿舍楼住宿人员性别情况
	* @return String
	*/
	public String queryDormZyxb(String params);
	
	/** 
	* @Description: TODO 查询宿舍住宿学生所在院系情况
	* @return String
	*/
	public String queryDormXsszyx(String params);
	
	/** 
	* @Description: TODO 查询宿舍楼详细信息
	* @return String
	*/
	public String queryDormInfo(String params);
	
	/** 
	* @Description: TODO 查询宿舍楼的楼层信息
	* @return String
	*/
	public String queryDormLc(String params);
	
	/** 
	* @Description: TODO 查询宿舍楼的房间信息
	* @return String
	*/
	public String queryDormFjxx(String params);
	
	/** 
	* @Description: TODO 查询宿舍入住率
	* @return String
	*/
	public String queryDormRzl(String params);
	
	/** 
	* @Title: queryDormjcrs 
	* @Description: TODO 查询宿舍楼进出人数
	* @return String
	*/
	public String queryDormjcrs(String params);
}
