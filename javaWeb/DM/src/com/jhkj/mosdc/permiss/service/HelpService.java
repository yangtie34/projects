package com.jhkj.mosdc.permiss.service;

/**
 * 一些辅助的数据请求
 * @author Administrator
 *
 */
public interface HelpService {
	
	/**
	 * 查询教学组织结构
	 * @param params
	 * @return
	 */
	public String queryJxzzjg(String params);
	/**
	 * 查询教学组织结构,排除班级
	 * @param params
	 * @return
	 */
	public String queryJxzzjgWithOutBj(String params);
	/**
	 * 查询用户组数据权限，并把数据权限绑定到教学组织结构上
	 * @param zzjgNList
	 * @param bjList
	 * @return
	 */
	public String queryJxzzjgWithGrouperDataPermiss(String params);
	/**
	 * 查询行政组织结构
	 */
	public String queryXzzzjg(String params);

}
