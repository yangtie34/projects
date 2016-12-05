package com.jhnu.product.common.school.service;

import java.util.List;

import com.jhnu.framework.entity.NodeAngularTree;
import com.jhnu.framework.entity.NodeTree;

public interface DeptTreeService {
	/**
	 * 获取行政组织机构树
	 * @return
	 */
	public Object getDeptPerms();
	/**
	 * 获取教学组织机构树
	 * @return
	 */
	public Object getDeptTeach();
	/**
	 * 获取教学组织机构树
	 * @return
	 */
	public Object getDeptTeach(String shiroTag);
	/**
	 * 获取行政组织机构树
	 * @return
	 */
	public Object getDept(String shiroTag);
	
	/**
	 * 获取组织机构树的JSON字符串
	 * @return
	 */
	public String getDeptJson();
	
	/**
	 * 获取教学组织机构树的JSON字符串
	 * @return
	 */
	public String getDeptTeachJson();
	Object getDeptTeachAglTree();
	Object getDeptAglTree();
	List<NodeTree> getDeptJL();
	List<NodeTree> getDeptTeachJL();
}
