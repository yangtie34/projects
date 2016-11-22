package com.jhkj.mosdc.sc.service;

public interface KcktjService {

	/**
	 * 查询课程库概况
	 * @return 
	 */
	public String queryKckgk(String params);

	/**
	 * 查询本学期课时量
	 * @return
	 */
	public String queryBxqksl(String params);
	
	/**
	 * 查询本学期开课情况
	 * @return
	 */
	public String queryBxqkkqk(String params);
	
	/**
	 * 查询课程分类情况
	 * @return
	 */
	public String queryKcflqk(String params);
	
	/**
	 * 查询课程承办单位情况
	 * @return
	 */
	public String queryKccbdw(String params);
	/**
	 * 查询各学院历史趋势。
	 * @param params
	 * @return
	 */
	public String queryYxZxs(String params);
}
