package com.jhkj.mosdc.sc.service;

import java.util.Map;

import com.jhkj.mosdc.sc.util.TreeNode;

/**
 * 输出服务接口
 * @author zhangzg
 *
 */
public interface ScService {
	/**
	 * 获取教学组织结构树数据。
	 * @param params
	 * @return
	 */
	public String getJxzzjgTree(String params);
	/**
	 * 获取院系组织结构树。
	 * @param params
	 * @return
	 */
	public String getYxzzjgTree(String params);
	/**
	 * 获取宿舍组织结构树。
	 * @param params
	 * @return
	 */
	public String getSszzjgTree(String params);
	/**
	 * 获取学校信息
	 * @param params
	 * @return
	 */
	public Map getXxxx();
	/**
	 * 根据id获得教学组织结构节点。
	 * @param nodeid
	 * @return
	 */
	public TreeNode getJxNodeById(String nodeid);
	/**
	 * 根据id获得行政组织结构节点。
	 * @param nodeid
	 * @return
	 */
	public TreeNode getXzNodeById(String nodeid);
	/**
	 * 获取科研组织结构树
	 * @param params
	 * @return
	 */
	public String getKyzzjgTree(String params);
	/**
	 * 获取院系单层次树
	 * @param params
	 * @return
	 */
	public String getYxCcTree(String params);
}
