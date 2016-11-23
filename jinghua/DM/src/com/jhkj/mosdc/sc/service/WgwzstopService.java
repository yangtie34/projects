package com.jhkj.mosdc.sc.service;

public interface WgwzstopService {
	/**
	 * 获得学霸信息
	 * @param params
	 * @return
	 */
	public String getXbInfo(String params);
	/**
	 * 依据绩点获取学霸信息
	 * @param params
	 * @return
	 */
	public String getXbInfoByJd(String params);
	/** 
	* @Title: wgTopTen 
	* @Description: TODO 晚归top10
	* @return String
	*/
	public String wgTopTen(String params);
	
	/** 
	* @Title: wzsTopTen 
	* @Description: TODO 未住宿top10
	* @return String
	*/
	public String wzsTopTen(String params);
	
	/** 
	* @Title: stuInfo 
	* @Description: TODO 学生信息查询
	* @return String
	*/
	public String stuInfo(String params);
	
	/** 
	* @Title: stuWginfo 
	* @Description: TODO 学生晚归信息
	* @return String
	*/
	public String stuWginfo(String params);
	
	/** 
	* @Title: stuWzsinfo 
	* @Description: TODO 学生未住宿信息
	* @return String
	*/
	public String stuWzsinfo(String params);
	
	/** 
	* @Title: stuXfinfo 
	* @Description: TODO 学生消费信息
	* @return String
	*/
	public String stuXfinfo(String params);

	/** 
	* @Title: stuZsinfo 
	* @Description: TODO 学生住宿信息
	* @return String
	*/
	public String stuZsinfo(String params);
	
	/** 
	* @Title: stuTsjy 
	* @Description: TODO 学生图书借阅
	* @return String
	*/
	public String stuTsjy(String params);
	
	/** 
	* @Title: stuKscj 
	* @Description: TODO 考试成绩
	* @return String
	*/
	public String stuKscj(String params);
	/**
	 * 资助及贫困生
	 * @param params
	 * @return
	 */
	public String zjpkInfo(String params);
}
