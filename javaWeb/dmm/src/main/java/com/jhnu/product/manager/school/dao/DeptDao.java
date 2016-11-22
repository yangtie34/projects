package com.jhnu.product.manager.school.dao;

import java.util.List;
import java.util.Map;

public interface DeptDao {
	
	/**
	 * 获取教学组织机构及人数
	 * @param ids
	 * @return
	 */
	public List<Map<String,Object>> getDeptTeach(String ids);
	
	/**
	 * 获取专业
	 * @param ids
	 * @return
	 */
	public List<Map<String,Object>> getMajorTeach(String dept_id);
	
	/**
	 * 获取行政组织机构及人数
	 * @param ids
	 * @return
	 */
	public List<Map<String,Object>> getDept(String ids);
	

	/**
	 * 获取子部门
	 * @param ids
	 * @return
	 */
	public List<Map<String,Object>> getDeptLeaf(String dept_id);

	/**
	 * 根据dept_id获取dept_name
	 * @param ids
	 * @return
	 */
	public List<Map<String,Object>> getDepts(String ids);
	
	/**
	 * 根据dept_id获取dept_name
	 * @param ids
	 * @return
	 */
	public List<Map<String,Object>> getTeaDepts(String ids);
	
}
