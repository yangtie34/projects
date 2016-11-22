package com.jhkj.mosdc.sc.service;

public interface JxpgXxjbxxService {
	/**
	 * 查询学校基本情况
	 */
	public String queryXxjbqk(String params);
	
	/**
	 * 自动填充学校基本情况
	 */
	public String autoFillXxjbqk(String params);
	
	/** 
	* 保存学校基本情况 
	*/
	public String saveXxjbqk(String params);
}
