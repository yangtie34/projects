package com.jhnu.syspermiss.school.service;

import java.util.List;

import com.jhnu.syspermiss.permiss.entity.DataServe;
import com.jhnu.syspermiss.school.entity.Dept;
import com.jhnu.syspermiss.school.entity.DeptTeach;

public interface DeptPermissionService {

	
	/**
	 * 获取教学组织机构权限集合
	 * @return 教学组织机构集合
	 */
	public List<DeptTeach> getAllDeptTeach();
	
	/**
	 * 获取行政组织机构权限集合
	 * @return 行政组织机构集合
	 */
	public List<Dept> getAllDept();
	
	/**
	 * 通过用户权限ID获取行政组织机构
	 * @param userPermId 用户权限ID
	 * @return
	 */
	public List<Dept> getDeptByUserPermsId(long userPermId);
	
	/**
	 * 通过用户权限ID获取教学组织机构
	 * @param userPermId 用户权限ID
	 * @return
	 */
	public List<DeptTeach> getDeptTeachByUserPermsId(long userPermId);
	
	/**
	 * 通过角色权限ID获取行政组织机构
	 * @param userPermId 角色权限ID
	 * @return
	 */
	public List<Dept> getDeptByRolePermsId(long rolePermId);
	
	/**
	 * 通过角色权限ID获取教学组织机构
	 * @param userPermId 角色权限ID
	 * @return
	 */
	public List<DeptTeach> getDeptTeachByRolePermsId(long rolePermId);
	/**
	 * 通过资源 获取教学组织机构 (系统自动获取相关参数)
	 * @param
	 * @return
	 */
	public Object getDeptTeachByShiroTag(String shiroTag);
	/**
	 * 通过数据服务查询出组织机构ID的SQL
	 * @param data 数据服务
	 * @return
	 */
	public String getDeptIdSqlbyDataServe(DataServe data);
	
	/**
	 * 通过数据服务查询出教学机构ID的SQL
	 * @param data 数据服务
	 * @return
	 */
	public String getDeptTeachIdSqlbyDataServe(DataServe data);
	/**
	 * 通过数据服务查询出辅导员辅导ID的SQL
	 * @param data 数据服务
	 * @return
	 */
	public String getDeptClassIdSqlbyDataServe(DataServe data);
	
	/**
	 * 通过数据服务查询出教职工所在院系的SQL
	 * @param data 数据服务
	 * @return
	 */
	public String getMyDeptSqlbyDataServe(DataServe data);
	/**
	 * 通过资源 获取组织机构 (系统自动获取相关参数)
	 * @param
	 * @return
	 */
	public Object getDeptByShiroTag(String shiroTag);
}