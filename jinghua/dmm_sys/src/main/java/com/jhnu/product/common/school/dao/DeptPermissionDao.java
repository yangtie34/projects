package com.jhnu.product.common.school.dao;

import java.util.List;
import java.util.Map;

import com.jhnu.framework.entity.NodeAngularTree;
import com.jhnu.framework.entity.NodeTree;
import com.jhnu.product.common.school.entity.Dept;
import com.jhnu.product.common.school.entity.DeptTeach;

public interface DeptPermissionDao {

	/**
	 * 重置 系统角色-权限-行政组织机构
	 * @param roleId 角色ID
	 * @param depts 行政组织机构IDs
	 */
	public void resetRoleDept(long roleId,List<Map> depts);
	
	/**
	 * 重置 特定用户-权限-行政组织机构
	 * @param userId 用户ID
	 * @param depes 行政组织机构IDs
	 */
	public void resetUserDept(long userId,List<Map> depts);
	
	/**
	 * 重置 系统角色-权限-教学组织机构
	 * @param roleId 角色ID
	 * @param deptTeachs 教学组织机构IDs
	 */
	public void resetRoleDeptTeach(long roleId,List<Map> deptTeachs);
	
	/**
	 * 重置 特定用户-权限-教学组织机构
	 * @param userId 用户ID
	 * @param deptTeachs 教学组织机构IDs
	 */
	public void resetUserDeptTeach(long userId,List<Map> deptTeachs);
	
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
	
	/**
	 * 通过数据服务查询出教职工所在院系的SQL
	 * @param data 数据服务
	 * @return
	 */
	public String getMyDeptSqlbyUsername(String username);
	
	
	public List<DeptTeach> getAllDeptTeachPerms();
	
	public List<Dept> getAllDeptPerms();
	/**
	 * 通过角色权限ID获取辅导员的SQL
	 * @param username
	 * @return
	 */
	public String getDeptClassIdSqlbyUserName(String username);

	public List<NodeAngularTree> getAllDeptPermsAgl();
	public List<NodeTree> getAllDeptTeachJL();
	public List<NodeAngularTree> getAllDeptTeachAgl();

	public List<NodeTree> getAllDeptPermsJL();
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
