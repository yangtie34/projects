package com.jhnu.syspermiss.school.dao;

import java.util.List;

import com.jhnu.syspermiss.school.entity.Dept;
import com.jhnu.syspermiss.school.entity.DeptTeach;


public interface DeptPermissionDao {
	
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
	 * 通过用户权限ID获取行政组织机构的SQL
	 * @param userPermId 用户权限ID
	 * @return
	 */
	public String getDeptIdSqlByUserPermsId(long userPermId);
	
	/**
	 * 通过用户权限ID获取教学组织机构的SQL
	 * @param userPermId 用户权限ID
	 * @return
	 */
	public String getDeptTeachIdSqlByUserPermsId(long userPermId);
	
	/**
	 * 通过角色权限ID获取行政组织机构的SQL
	 * @param userPermId 角色权限ID
	 * @return
	 */
	public String getDeptIdSqlByRolePermsId(long rolePermId);
	
	/**
	 * 通过角色权限ID获取教学组织机构的SQL
	 * @param userPermId 角色权限ID
	 * @return
	 */
	public String getDeptTeachIdSqlByRolePermsId(long rolePermId);
	
	
	public List<DeptTeach> getAllDeptTeachPerms();
	
	/**
	 * 通过数据服务查询出教职工所在院系的SQL
	 * @param data 数据服务
	 * @return
	 */
	public String getMyDeptSqlbyUsername(String username);
	
	public List<Dept> getAllDeptPerms();
	/**
	 * 通过角色权限ID获取辅导员的SQL
	 * @param userPermId 角色权限ID
	 * @return
	 */
	public String getDeptClassIdSqlbyUserName(String username);
	/**
	 * 通过用户名和资源 获取组织机构 (系统自动获取相关参数)
	 * @param
	 * @return
	 */
	public List<DeptTeach> getDeptTeachByUserNameAndShiroTag(String username,String shiroTag);
	/**
	 * 通过用户名和资源 获取组织机构 (系统自动获取相关参数)
	 * @param
	 * @return
	 */
	public List<Dept> getDeptByUserNameAndShiroTag(String username,String shiroTag);
}
